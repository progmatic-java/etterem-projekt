package hu.progmatic.kozos.etterem.etel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EtelController {

    @GetMapping("/etterem/asztal/{asztalId}/etel")
    public String etterem(@PathVariable Integer asztalId, Model model) {
        model.addAttribute("asztalId", asztalId);
        return "/etterem/etel";
    }

}
