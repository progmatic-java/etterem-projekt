package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SzamlaController {
    @Autowired
    private SzamlaService szamlaService;

    @GetMapping("/etterem/asztal/{asztalId}/szamla")
    public String etterem(@ModelAttribute("allRendeles")
            @PathVariable Integer asztalId, Model model) {
        model.addAttribute("asztalId", asztalId);
        return "etterem/szamla";
    }

    @PostMapping("/etterem/asztal/{asztalId}/szamla")
    public String szamla(
            @ModelAttribute("allRendeles")
            @PathVariable Integer asztalId,
            Model model) {
        szamlaMegjelenitese(model, asztalId);
        return "etterem/asztal/szamla";
    }

    private String szamlaMegjelenitese(Model model, Integer asztalId) {
        Szamla szamla = szamlaService.findSzamlaByAsztalId(asztalId);
        SzamlaDto dto = szamlaService.szamlaDtoBuilder(szamla);
        model.addAttribute("rendelesDto", dto);
        model.addAttribute("allRendeles", dto.getRendelesek());
        return "etterem/asztal/szamla";
    }

    @ModelAttribute("szamlaDto")
    SzamlaDto szamlaDto() {
        return SzamlaDto.builder().build();
    }

    @ModelAttribute("allRendeles")
    List<RendelesDto> allRendeles() {
        return List.of();
    }
}
