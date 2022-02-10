package hu.progmatic.kozos.etterem;

import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.asztal.TableViewDto;
import hu.progmatic.kozos.etterem.leltar.Termek;
import hu.progmatic.kozos.etterem.leltar.TermekService;
import hu.progmatic.kozos.etterem.leltar.Tipus;
import hu.progmatic.kozos.etterem.rendeles.CreateRendelesCommand;
import hu.progmatic.kozos.etterem.rendeles.RendelesService;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    TermekService termekService;

    private Integer tesztAsztalId;
    private Termek tesztTermek;
    private Rendeles tesztOrder;

    @BeforeEach
    void setUp() {
        tesztAsztalId = asztalService.getIdByNev("1. ASZTAL");
        tesztTermek = termekService.getByName("Paradicsom leves");
    }

    @Test
    void addOrderToAsztalTest() {
        CreateRendelesCommand command = CreateRendelesCommand.builder()
                .asztalId(tesztAsztalId)
                .etteremTermekId(tesztTermek.getId())
                .mennyiseg(2)
                .build();
        tesztOrder = rendelesService.create(command);
        TableViewDto dto = asztalService.getTableViewDto(tesztAsztalId, Tipus.UDITO);
        assertEquals("1. ASZTAL", dto.getNev());
        assertEquals(tesztAsztalId, dto.getId());
        assertThat(dto.getRendelesDtoList()).hasSize(1);
        TableViewDto.RendelesDto order = dto.getRendelesDtoList().get(0);
        assertEquals("Paradicsom leves", order.getEtteremTermekNev());
        assertEquals(2, order.getMennyiseg());
        assertEquals(tesztOrder.getId(), order.getRendelesId());
    }

    @Test
    void mennyisegHozzaadasaTest() {

    }
}