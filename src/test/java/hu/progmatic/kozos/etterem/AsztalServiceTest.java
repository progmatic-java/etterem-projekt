package hu.progmatic.kozos.etterem;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalDto;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AsztalServiceTest {
  private Asztal tesztAsztal;

  @Autowired
  private AsztalService asztalService;

  @BeforeEach
  void setUp() {
    asztalService.afterPropertiesSet();
    tesztAsztal = asztalService.getById(asztalService.getIdByNev("1. asztal"));
  }

  @Test
  @DisplayName("Asztal kikérése id alapján")
  @Disabled
  void getByIdTest() {
    Asztal asztal = asztalService.getById(tesztAsztal.getId());
    AsztalDto dto = asztalService.buildAsztalDto(asztal);
    assertNotNull(dto.getId());
    assertEquals("1. asztal", dto.getNev());
  }
}