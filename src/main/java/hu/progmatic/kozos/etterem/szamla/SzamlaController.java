package hu.progmatic.kozos.etterem.szamla;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SzamlaController {
  @Autowired
  private SzamlaService szamlaService;

  @GetMapping("/etterem/asztal/{asztalId}/szamla")
  public String etterem(
      @PathVariable Integer asztalId,
      Model model
  ) {
    SzamlaDto dto = szamlaService.szamlaDtoBuilder(szamlaService.findSzamlaByAsztalId(asztalId));
    model.addAttribute("szamlaDto", dto);
    return "etterem/szamla";
  }

  @GetMapping("/etterem/asztal/{asztalId}/splitSzamla")
  public String splitSzamla(
      @PathVariable Integer asztalId,
      Model model
  ) {
    szamlaService.splitSzamla(szamlaService.findSzamlaByAsztalId(asztalId));
    SzamlaDto dto = szamlaService.szamlaDtoBuilder(szamlaService.findSzamlaByAsztalId(asztalId));
    model.addAttribute("szamlaDto", dto);
    return "etterem/szamla";
  }

  @PostMapping("/etterem/asztal/{asztalId}/addToSzamlaSplit/{termekId}")
  public String addToSzamlaSplit(
      @PathVariable Integer asztalId,
      @PathVariable Integer termekId
  ) {
    szamlaService.addToSzamlaSplit(asztalId, termekId);
    return "redirect:/etterem/asztal/" + asztalId + "/szamla";
  }

  @ModelAttribute("szamlaDto")
  SzamlaDto szamlaDto() {
    return SzamlaDto.builder().build();
  }
}
