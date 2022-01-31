package hu.progmatic.etterem.etterem;

import hu.progmatic.kozos.etterem.leltar.EtteremTermek;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekService;
import hu.progmatic.kozos.etterem.leltar.Tipus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EtteremEtteremTermekServiceTest {
  @Autowired
  EtteremTermekService service;

  @Test
  @DisplayName("Létrehozás és kiolvasás teszt")
  void createAndRead() {
    EtteremTermek newEntity = EtteremTermek.builder()
        .nev("Kóla")
        .ar(400)
        .tipus(Tipus.UDITO)
        .build();
    EtteremTermek savedEntity = service.create(newEntity);
    EtteremTermekDto dto = service.getDto(savedEntity.getId());

    assertNotNull(dto.getId());
    assertEquals("Kóla", dto.getNev());
    assertEquals(400, dto.getAr());
  }

  @Test
  @DisplayName("Törlés és létezés ellenőrzése")
  void existAndDelete() {
    EtteremTermek newEntity = EtteremTermek.builder()
        .nev("Steak")
        .ar(2700)
        .tipus(Tipus.MARHAETEL)
        .build();
    EtteremTermek savedEntity = service.create(newEntity);
    EtteremTermekDto dto = service.getDto(savedEntity.getId());

    assertTrue(service.isExists(dto.getId()));
    service.deleteById(dto.getId());
    assertFalse(service.isExists(dto.getId()));
  }

  @Nested
  @DisplayName("Tesztek létező entitással")
  class LetezoEntitasTest {
    EtteremTermek entity;

    @BeforeEach
    void setUp() {
      EtteremTermek newEntity = EtteremTermek.builder()
          .nev("RedBull")
          .ar(450)
          .tipus(Tipus.UDITO)
          .build();
      entity = service.create(newEntity);
    }

    @Test
    @DisplayName("Adatok módosítása")
    void update() {
      service.update(entity.getId(), "Hell", 200, Tipus.UDITO);
      EtteremTermekDto dto = service.getDto(entity.getId());
      assertEquals("Hell", dto.getNev());
      assertEquals(200, dto.getAr());
    }
  }
}