package hu.progmatic.kozos.etterem.etel;

import hu.progmatic.kozos.etterem.asztal.AsztalRepository;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.asztal.TableViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EtelController {
  @Autowired
  AsztalService asztalService;

  @GetMapping("/etterem/asztal/{asztalId}/etel")
  public String etterem(@PathVariable Integer asztalId, Model model) {
    model.addAttribute("tableViewDto", asztalService.getTableViewDto(asztalId));
    return "/etterem/etel";
  }

  @ModelAttribute("tableViewDto")
  public TableViewDto tableViewDto() {
    return TableViewDto.builder().build();
  }
}
