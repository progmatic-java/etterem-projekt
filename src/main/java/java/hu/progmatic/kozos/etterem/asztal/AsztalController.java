package java.hu.progmatic.kozos.etterem.asztal;

import hu.progmatic.kozos.etterem.rendeles.RendelesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AsztalController {
    @Autowired
    RendelesService rendelesService;

    @GetMapping("/etterem/asztal")
    public String etterem() {
        return "/etterem/asztal";
    }

    @GetMapping("/etterem/asztal/{asztalId}/rendeles/{rendelesId}")
    public String asztalFilter(@PathVariable Asztal asztal, Model model) {
        model.addAttribute(
                "findAllByAsztal",
                rendelesService.findAllByAsztal(asztal)
        );
        return "/etterem/rendeles";
    }
}

