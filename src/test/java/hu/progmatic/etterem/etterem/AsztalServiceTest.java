package hu.progmatic.etterem.etterem;

import hu.progmatic.kozos.etterem.asztal.AsztalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AsztalServiceTest {

  @Autowired
  private AsztalService asztalService;

  @Test
  void getIdByName() {
    assertNotNull(asztalService.getIdByNev("1. asztal"));
  }
}