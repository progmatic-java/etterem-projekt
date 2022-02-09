package hu.progmatic.kozos.etterem.termekfooldal;

import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.asztal.TableViewDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermek;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekService;
import hu.progmatic.kozos.etterem.leltar.Tipus;
import hu.progmatic.kozos.etterem.rendeles.CreateRendelesCommand;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import hu.progmatic.kozos.etterem.rendeles.RendelesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TermekFooldalController {
  @Autowired
  private AsztalService asztalService;
  @Autowired
  private RendelesService rendelesService;
  @Autowired
  private EtteremTermekService etteremTermekService;

  @GetMapping("/etterem/asztal/{asztalId}/{asztalFeluletTipus}")
  public String itemMain(
      @PathVariable Integer asztalId,
      @PathVariable AsztalFeluletTipus asztalFeluletTipus,
      Model model) {
    TableViewDto dto = asztalService.getTableViewDto(asztalId, asztalFeluletTipus);
    model.addAttribute("tableViewDto", dto);
      if (asztalFeluletTipus == AsztalFeluletTipus.ITAL || asztalFeluletTipus == AsztalFeluletTipus.ETEL) {
          dto.setVisszaGombLink("/etterem/asztal/" + asztalId + "/KEZDOLAP");
      } else if (asztalFeluletTipus == AsztalFeluletTipus.KEZDOLAP) {
          dto.setVisszaGombLink("/etterem/asztal/");
      }
    getGombDtoList(asztalId, asztalFeluletTipus, model);
    return "etterem/termek_fooldal";
  }

  @GetMapping("/etterem/asztal/{asztalId}/{asztalFeluletTipus}/tipus/{tipus}")
  public String dishes(
      @PathVariable Integer asztalId,
      @PathVariable AsztalFeluletTipus asztalFeluletTipus,
      @PathVariable Tipus tipus,
      Model model
  ) {
    model.addAttribute(
        "filteredByTipus",
        etteremTermekService.findAllByTipus(tipus)
    );
      TableViewDto dto = asztalService.getTableViewDto(asztalId, tipus, asztalFeluletTipus);
      model.addAttribute("tableViewDto", dto);
      dto.setVisszaGombLink("/etterem/asztal/" + asztalId + "/" + asztalFeluletTipus.name());
      return "etterem/termek_fooldal";
  }

  @PostMapping("/etterem/asztal/{asztalId}/{asztalFeluletTipus}/tipus/{tipus}")
  public String createOrder(
      @PathVariable Integer asztalId,
      @PathVariable Tipus tipus,
      @PathVariable AsztalFeluletTipus asztalFeluletTipus,
      @ModelAttribute("createRendelesCommand") @Valid CreateRendelesCommand command) {
    asztalId = asztalService.getIdByAsztalSzam(asztalId);
    command.setAsztalId(asztalId);
    if (rendelesService.rendelesTartalmazzaATermeket(command)) {
      rendelesService.mennyisegNovelese(asztalId, command.getEtteremTermekId());
    } else {
      rendelesService.create(command);

  }

    return "redirect:/etterem/asztal/" + asztalId + "/" + asztalFeluletTipus.name() + "/tipus/" + tipus;
  }

  @PostMapping("/etterem/asztal/{asztalId}/mennyisegNoveleseTipusOldalon/{asztalFeluletTipus}/{tipus}/{termekNeve}")
  public String mennyisegNoveleseTipusOldalon(
      @PathVariable Integer asztalId,
      @PathVariable AsztalFeluletTipus asztalFeluletTipus,
      @PathVariable Tipus tipus,
      @PathVariable String termekNeve,
      Model model
  ) {
    rendelesService.mennyisegNovelese(asztalId, termekNeve);
    TableViewDto dto = asztalService.getTableViewDto(asztalId, tipus, asztalFeluletTipus);
    model.addAttribute("tableViewDto", dto);
    model.addAttribute("filteredByTipus", etteremTermekService.findAllByTipus(tipus));
      return "redirect:/etterem/asztal/" + asztalId + "/" + asztalFeluletTipus.name() + "/tipus/" + tipus;
  }

  @PostMapping("/etterem/asztal/{asztalId}/mennyisegCsokkenteseTipusOldalon/{asztalFeluletTipus}/{tipus}/{termekNeve}")
  public String mennyisegCsokkenteseTipusOldalon(
      @PathVariable Integer asztalId,
      @PathVariable AsztalFeluletTipus asztalFeluletTipus,
      @PathVariable Tipus tipus,
      @PathVariable String termekNeve,
      Model model
  ) {
    rendelesService.mennyisegCsokkentese(asztalId, termekNeve);
    TableViewDto dto = asztalService.getTableViewDto(asztalId, tipus, asztalFeluletTipus);
    model.addAttribute("tableViewDto", dto);
    model.addAttribute("filteredByTipus", etteremTermekService.findAllByTipus(tipus));
      return "redirect:/etterem/asztal/" + asztalId + "/" + asztalFeluletTipus.name() + "/tipus/" + tipus;
  }

  @PostMapping("/etterem/asztal/{asztalId}/mennyisegCsokkenteseKezdolapon/{asztalFeluletTipus}/{termekNeve}")
  public String mennyisegCsokkenteseKezdolapon(
      @PathVariable Integer asztalId,
      @PathVariable String termekNeve,
      @PathVariable AsztalFeluletTipus asztalFeluletTipus,
      Model model
  ) {
    rendelesService.mennyisegCsokkentese(asztalId, termekNeve);
    TableViewDto dto = asztalService.getTableViewDto(asztalId, asztalFeluletTipus);
    model.addAttribute("tableViewDto", dto);
    getGombDtoList(asztalId, asztalFeluletTipus, model);
      return "redirect:/etterem/asztal/" + asztalId + "/" + asztalFeluletTipus.name();
  }

  @PostMapping("/etterem/asztal/{asztalId}/mennyisegNoveleseKezdolapon/{asztalFeluletTipus}/{termekNeve}")
  public String mennyisegNoveleseKezdolapon(
      @PathVariable Integer asztalId,
      @PathVariable String termekNeve,
      @PathVariable AsztalFeluletTipus asztalFeluletTipus,
      Model model
  ) {
    rendelesService.mennyisegNovelese(asztalId, termekNeve);
    TableViewDto dto = asztalService.getTableViewDto(asztalId, asztalFeluletTipus);
    model.addAttribute("tableViewDto", dto);
    getGombDtoList(asztalId, asztalFeluletTipus, model);
      return "redirect:/etterem/asztal/" + asztalId + "/" + asztalFeluletTipus.name();
  }

  private void getGombDtoList(Integer asztalId, AsztalFeluletTipus tipus, Model model) {
    List<GombDto> gombok;
    if (tipus == AsztalFeluletTipus.ETEL) {
      gombok = List.of(
          new GombDto("LEVES", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.LEVES.name()),
          new GombDto("ELŐÉTEL", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.ELOETEL.name()),
          new GombDto("SERTÉSÉTEL", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.SERTESETEL.name()),
          new GombDto("HALÉTEL", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.HALETEL.name()),
          new GombDto("MARHA", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.MARHAETEL.name()),
          new GombDto("DESSZERT", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.DESSZERT.name()),
          new GombDto("EXTRA", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.EXTRA.name())
      );
    } else if (tipus == AsztalFeluletTipus.ITAL) {
      gombok = List.of(
          new GombDto("ALKOHOL", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.ALKOHOL.name()),
          new GombDto("RÖVIDITAL", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.ROVIDITAL.name()),
          new GombDto("KOKTÉL", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.KOKTEL.name()),
          new GombDto("ÜDÍTŐ", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.UDITO.name()),
          new GombDto("FORRÓ ITAL", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.FORROITAL.name()),
          new GombDto("KÁVÉ", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.KAVE.name()),
          new GombDto("EXTRA", "/etterem/asztal/" + asztalId + "/" + tipus + "/tipus/" + Tipus.EXTRA.name())
      );
    } else {
      gombok = List.of(
          new GombDto("ÉTEL", "/etterem/asztal/" + asztalId + "/" + AsztalFeluletTipus.ETEL.name()),
          new GombDto("ITAL", "/etterem/asztal/" + asztalId + "/" + AsztalFeluletTipus.ITAL.name())
      );
    }
    model.addAttribute(
        "gombDtoList",
        gombok
    );
  }

  @ModelAttribute("tableViewDto")
  public TableViewDto tableViewDto() {
    return TableViewDto.builder().build();
  }

  @ModelAttribute("gombDtoList")
  public List<GombDto> gombDtoList() {
    return List.of();
  }

  @ModelAttribute("rendelesItems")
  List<EtteremTermekDto> rendelesItems() {
    return List.of();
  }

  @ModelAttribute("formItem")
  public Rendeles formItem() {
    return new Rendeles();
  }

  @ModelAttribute("filteredByTipus")
  public List<EtteremTermek> filteredByTipus() {
    return List.of();
  }

  private void clearFormItem(Model model) {
    model.addAttribute("formItem", formItem());
  }

  private void refreshAllItem(Model model) {
    model.addAttribute("rendelesItems", rendelesItems());
  }
}
