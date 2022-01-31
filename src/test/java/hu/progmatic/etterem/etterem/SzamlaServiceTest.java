package hu.progmatic.etterem.etterem;


import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.szamla.Szamla;
import hu.progmatic.kozos.etterem.szamla.SzamlaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SzamlaServiceTest {
  @Autowired
  private SzamlaService service;
  @Autowired
  private AsztalService asztalService;
  private Integer tesztAsztalId;

  @BeforeEach
  void setUp() {
    tesztAsztalId = asztalService.getIdByNev("1. asztal");
  }

  @Test
  void createAndRead() {
    Szamla szamla = service.createSzamlaForAsztal(tesztAsztalId);
    assertNotNull(szamla.getId());
    assertEquals("1. asztal", szamla.getAsztal().getNev());
  }


}