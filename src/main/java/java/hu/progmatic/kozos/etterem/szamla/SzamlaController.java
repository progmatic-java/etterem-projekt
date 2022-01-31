package java.hu.progmatic.kozos.etterem.szamla;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SzamlaController {

    @GetMapping("/etterem/asztal/{asztalId}/szamla")
    public String etterem(@PathVariable Integer asztalId, Model model) {
        model.addAttribute("asztalId", asztalId);
        return "etterem/szamla";
    }
}
