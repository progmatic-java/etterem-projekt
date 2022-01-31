package hu.progmatic.kozos.etterem.rendeles;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.asztal.TableViewDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
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
public class RendelesController {
  @Autowired
  private RendelesService rendelesService;
  @Autowired
  private AsztalService asztalService;


  @GetMapping("/etterem/asztal/{asztalId}/rendeles")
  public String rendelesList(Model model) {
    return rendelesek();
  }

  private String rendelesek() {
    return "/etterem/termek_fooldal";
  }

  @GetMapping("/etterem/table/{asztalId}/{orderId}")
  public String etterem(@PathVariable Integer asztalId, Model model) {
    model.addAttribute("asztalId", asztalId);
    model.addAttribute("rendelesItems", rendelesItems());
    return "/etterem/rendeles";
  }

  @PostMapping("/etterem/asztal/{asztalId}/rendeles")
  public String createOrder(
      @PathVariable Integer asztalId,
      @ModelAttribute("createRendelesCommand") @Valid CreateRendelesCommand command,
      BindingResult bindingResult,
      Model model) {
    if (!bindingResult.hasErrors()) {
      asztalId = asztalService.getIdByAsztalSzam(asztalId);
      command.setAsztalId(asztalId);
      rendelesService.create(command);
      model.addAttribute("tableViewDto", asztalService.getTableViewDto(asztalId));
      refreshAllItem(model);
      clearFormItem(model);
    }
    return rendelesek();
  }

  @PostMapping("/etterem/asztal/{asztalId}/mennyisegNovelese/{termekNeve}")
  public String mennyisegNovelese(
      @PathVariable Integer asztalId,
      @PathVariable String termekNeve,
      Model model
  ) {
    TableViewDto dto = asztalService.getTableViewDto(asztalId);
    asztalService.mennyisegNovelese(asztalId, termekNeve);
    model.addAttribute("tableViewDto", dto);
    return "etterem/termek_fooldal";
  }

  @ModelAttribute("rendelesItems")
  List<EtteremTermekDto> rendelesItems() {
    return List.of();
  }

  @ModelAttribute("formItem")
  public Rendeles formItem() {
    return new Rendeles();
  }

  @ModelAttribute("tableViewDto")
  public TableViewDto tableViewDto() {
    return TableViewDto.builder().build();
  }

  private void clearFormItem(Model model) {
    model.addAttribute("formItem", formItem());
  }

  private void refreshAllItem(Model model) {
    model.addAttribute("rendelesItems", rendelesItems());
  }
}
