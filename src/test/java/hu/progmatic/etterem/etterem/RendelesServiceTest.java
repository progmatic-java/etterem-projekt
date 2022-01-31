package hu.progmatic.etterem.etterem;

import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.asztal.TableViewDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermek;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekService;
import hu.progmatic.kozos.etterem.rendeles.CreateRendelesCommand;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import hu.progmatic.kozos.etterem.rendeles.RendelesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RendelesServiceTest {
  @Autowired
  RendelesService rendelesService;
  @Autowired
  AsztalService asztalService;
  @Autowired
  EtteremTermekService etteremTermekService;
  private Integer tesztAsztalId;
  private EtteremTermek tesztEtteremTermek;
  private Rendeles tesztOrder;

  @BeforeEach
  void setUp() {
    tesztAsztalId = asztalService.getIdByNev("1. asztal");
    tesztEtteremTermek = etteremTermekService.getByName("Paradicsom leves");

  }

  @Test
  void addOrderToAsztalTest() {
    CreateRendelesCommand command = CreateRendelesCommand.builder()
        .asztalId(tesztAsztalId)
        .etteremTermekId(tesztEtteremTermek.getId())
        .mennyiseg(2)
        .build();
    tesztOrder = rendelesService.create(command);
    TableViewDto dto = asztalService.getTableViewDto(tesztAsztalId);
    assertEquals("1. asztal", dto.getNev());
    assertEquals(tesztAsztalId, dto.getId());
    assertThat(dto.getRendelesDtoList()).hasSize(1);
    TableViewDto.RendelesDto order = dto.getRendelesDtoList().get(0);
    assertEquals("Paradicsom leves", order.getEtteremTermekNev());
    assertEquals(2, order.getMennyiseg());
    assertEquals(tesztOrder.getId(), order.getRendelesId());
  }
}