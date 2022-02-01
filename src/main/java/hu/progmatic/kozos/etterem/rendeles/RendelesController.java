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
}
