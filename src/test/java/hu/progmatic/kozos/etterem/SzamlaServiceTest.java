package hu.progmatic.kozos.etterem;

import hu.progmatic.kozos.etterem.asztal.AsztalDto;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.rendeles.CreateRendelesCommand;
import hu.progmatic.kozos.etterem.rendeles.RendelesService;
import hu.progmatic.kozos.etterem.szamla.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SzamlaServiceTest {
  private AsztalDto tesztAsztalDto;
  private SzamlaDto tesztSzamlaDto;
  private Integer tesztAsztalId;
  private SzamlaTetelDto tesztSzamlaTetelDto;

  @Autowired
  private SzamlaService szamlaService;
  @Autowired
  private AsztalService asztalService;
  @Autowired
  private RendelesService rendelesService;

  @BeforeEach
  void setUp() {
    tesztAsztalId = asztalService.getIdByNev("2. ASZTAL");
    tesztAsztalDto = asztalService.getAsztalDtoById(tesztAsztalId);
    rendelesService.create(CreateRendelesCommand.builder()
        .asztalId(tesztAsztalId)
        .etteremTermekId(rendelesService.findTermekByNev("Paradicsom leves").getId())
        .mennyiseg(1)
        .build());
  }

  @AfterEach
  void tearDown() {
    try {
      rendelesService.mennyisegCsokkentese(tesztAsztalId, "Paradicsom leves");
    } catch (NoSuchElementException exception) {
      boolean nemLetezikARendeles = true;
    }
  }

  @Test
  @DisplayName("Számla létrehozása asztalhoz")
  void szamlaLetrehozasaAsztalhozTest() {
    assertNull(tesztAsztalDto.getSzamlaDto());
    szamlaService.createSzamlaForAsztal(tesztAsztalId);
    tesztAsztalDto = asztalService.getAsztalDtoById(tesztAsztalId);
    assertNotNull(tesztAsztalDto.getSzamlaDto());
  }

  @Nested
  @DisplayName("Tesztek létező számlával")
  class LetezoSzamlaTest {

    @BeforeEach
    void setUp() {
      szamlaService.createSzamlaForAsztal(tesztAsztalId);
      tesztSzamlaDto = szamlaService.buildSzamlaDto(tesztAsztalId);
    }

    @Test
    @DisplayName("Számla ketté választása")
    void splitSzamlaTest() {
      SzamlaDto dto = szamlaService.buildSzamlaDto(tesztAsztalId);
      assertFalse(dto.isSplit());
      szamlaService.splitSzamla(tesztAsztalId);
      dto = szamlaService.buildSzamlaDto(tesztAsztalId);
      assertTrue(dto.isSplit());
    }

    @Test
    @DisplayName("Kettéválasztás visszavonása")
    void cancelSplitTest() {
      szamlaService.splitSzamla(tesztAsztalId);
      SzamlaDto dto = szamlaService.buildSzamlaDto(tesztAsztalId);
      assertTrue(dto.isSplit());
      szamlaService.cancelSplit(tesztAsztalId);
      dto = szamlaService.buildSzamlaDto(tesztAsztalId);
      assertFalse(dto.isSplit());
    }

    @Test
    @DisplayName("Számla megkeresése asztalId alapján")
    void findSzamlaByAsztalIdTest() {
      Szamla talaltSzamla = szamlaService.findSzamlaByAsztalId(tesztAsztalId);
      assertEquals(tesztSzamlaDto.getId(), talaltSzamla.getId());
    }

    @Test
    @DisplayName("Számla fizetése")
    void szamlaFizeteseTest() {
      assertTrue(tesztSzamlaDto.getVegosszeg() != 0);
      szamlaService.szamlaFizetese(tesztAsztalId);
      tesztSzamlaDto = szamlaService.buildSzamlaDto(tesztAsztalId);
      assertEquals(0, tesztSzamlaDto.getVegosszeg());
      assertEquals(0, tesztSzamlaDto.getFizetettVegosszeg());
      assertEquals(0, tesztSzamlaDto.getTetelek().size());
    }

    @Nested
    @DisplayName("Tesztek kettéválasztott számlával")
    class SlitSzamlaTest {

      @BeforeEach
      void setUp() {
        szamlaService.splitSzamla(tesztAsztalId);
        tesztSzamlaTetelDto = getSzamlaTetelDto();
      }

      @AfterEach
      void tearDown() {
        tesztSzamlaTetelDto = SzamlaTetelDto.builder().build();
      }

      @Test
      @DisplayName("Hozzáadás ideiglenes számlához")
      void addToSzamlaSplitTest() {
        tesztSzamlaTetelDto = getSzamlaTetelDto();
        assertEquals(0, tesztSzamlaTetelDto.getFizetettMennyiseg());
        assertEquals(1, tesztSzamlaTetelDto.getNemFizetettMennyiseg());
        addToSplitSzamlaAndBuildDtos();
        assertEquals(1, tesztSzamlaTetelDto.getFizetettMennyiseg());
        assertEquals(0, tesztSzamlaTetelDto.getNemFizetettMennyiseg());
      }

      @Test
      @DisplayName("Elvonás ideiglenes számlából")
      void removeFromSzamlaSplitTest() {
        addToSplitSzamlaAndBuildDtos();
        assertEquals(1, tesztSzamlaTetelDto.getFizetettMennyiseg());
        assertEquals(0, tesztSzamlaTetelDto.getNemFizetettMennyiseg());
        szamlaService.removeFromSzamlaSplit(tesztAsztalId, tesztSzamlaTetelDto.getRendelesDto().getTermekDto().getId());
        tesztSzamlaDto = szamlaService.buildSzamlaDto(tesztAsztalId);
        tesztSzamlaTetelDto = getSzamlaTetelDto();
        assertEquals(0, tesztSzamlaTetelDto.getFizetettMennyiseg());
        assertEquals(1, tesztSzamlaTetelDto.getNemFizetettMennyiseg());
      }

      @Test
      @DisplayName("Számlatétel eltávolítása")
      void szamlaTetelEltavolitasaTest() {
        assertNotNull(tesztSzamlaTetelDto.getId());
        szamlaService.szamlaTetelEltavolitasa(tesztAsztalId, tesztSzamlaTetelDto.getRendelesDto().getId());
        tesztSzamlaDto = szamlaService.buildSzamlaDto(tesztAsztalId);
        String message = "";
        try {
          tesztSzamlaTetelDto = getSzamlaTetelDto();
        } catch (NoSuchElementException exception) {
          message = exception.getMessage();
        }
        assertEquals("No value present", message);
      }

      @Test
      @DisplayName("Külön számla fizetése")
      void kulonSzamlaFizeteseTest() {
        addToSplitSzamlaAndBuildDtos();
        assertTrue(tesztSzamlaDto.isSplit());
        assertEquals(1, tesztSzamlaTetelDto.getFizetettMennyiseg());
        szamlaService.kulonSzamlaFizetese(tesztAsztalId);
        tesztSzamlaDto = szamlaService.buildSzamlaDto(tesztAsztalId);
        assertFalse(tesztSzamlaDto.isSplit());
        String message = "";
        try {
          tesztSzamlaTetelDto = getSzamlaTetelDto();
        } catch (NoSuchElementException exception) {
          message = exception.getMessage();
        }
        assertEquals("No value present", message);
      }

      private void addToSplitSzamlaAndBuildDtos() {
        szamlaService.addToSzamlaSplit(tesztAsztalId, tesztSzamlaTetelDto.getRendelesDto().getTermekDto().getId());
        tesztSzamlaDto = szamlaService.buildSzamlaDto(tesztAsztalId);
        tesztSzamlaTetelDto = getSzamlaTetelDto();
      }

      private SzamlaTetelDto getSzamlaTetelDto() {
        return tesztSzamlaDto.getTetelek().stream()
            .filter(szamlaTetelDto -> szamlaTetelDto.getRendelesDto().getTermekDto().getNev().equals("Paradicsom leves"))
            .findFirst()
            .orElseThrow();
      }
    }
  }
}