package hu.progmatic.kozos.etterem.szamla;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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

  @ModelAttribute("szamlaDto")
  SzamlaDto szamlaDto() {
    return SzamlaDto.builder().build();
  }
}
