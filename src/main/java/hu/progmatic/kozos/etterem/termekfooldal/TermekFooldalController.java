package hu.progmatic.kozos.etterem.termekfooldal;


import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.asztal.TableViewDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TermekFooldalController {
  @Autowired
  private EtteremTermekService etteremTermekService;
  @Autowired
  private AsztalService asztalService;

  @GetMapping("/etterem/asztal/{asztalId}/termek_fooldal")
  public String itemMain(
      @PathVariable Integer asztalId,
      Model model) {
    TableViewDto dto = asztalService.getTableViewDto(asztalId);
    model.addAttribute("tableViewDto", dto);
    return "etterem/termek_fooldal";
  }

  @ModelAttribute("tableViewDto")
  public TableViewDto tableViewDto() {
    return TableViewDto.builder().build();
  }
}
