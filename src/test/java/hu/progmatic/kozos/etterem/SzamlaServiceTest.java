package hu.progmatic.kozos.etterem;


import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.szamla.SzamlaService;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  void setUp() {
    tesztAsztalId = asztalService.getIdByNev("1. asztal");
    service.createSzamlaForAsztal(tesztAsztalId);
  }

}