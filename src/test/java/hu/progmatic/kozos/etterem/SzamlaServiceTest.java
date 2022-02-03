package hu.progmatic.kozos.etterem;


import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.leltar.EtteremTermek;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import hu.progmatic.kozos.etterem.szamla.Szamla;
import hu.progmatic.kozos.etterem.szamla.SzamlaDto;
import hu.progmatic.kozos.etterem.szamla.SzamlaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SzamlaServiceTest {
  @Autowired
  private SzamlaService service;
  @Autowired
  private AsztalService asztalService;
  private Integer tesztAsztalId;
  private Rendeles rendeles;

  @BeforeEach
  void setUp() {
    tesztAsztalId = asztalService.getIdByNev("1. asztal");
  }

  @Test
  void createAndRead() {
    SzamlaDto szamla = service.createSzamlaForAsztal(tesztAsztalId);
    assertNotNull(szamla.getId());
    assertEquals("1. asztal", asztalService.getById(szamla.getAsztalId()).getNev());
  }

  @Test
  void szamlaAdd(){
    Szamla szamla = service.findSzamlaByAsztalId(tesztAsztalId);
    SzamlaDto dto = service.createSzamlaForAsztal(tesztAsztalId);
    assertNotNull(dto.getId());
    assertThat(dto.getSzamlaTetelek().size()==0);
  }


}