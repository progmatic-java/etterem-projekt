package java.hu.progmatic.kozos.etterem.termekfooldal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.hu.progmatic.kozos.etterem.leltar.EtteremTermekService;

@Controller
public class TermekFooldalController {
    @Autowired
    private EtteremTermekService etteremTermekService;

    @GetMapping("/etterem/asztal/{asztalId}/termek_fooldal")
    public String itemMain(@PathVariable Integer asztalId, Model model) {
        model.addAttribute("asztalId", asztalId);
        model.addAttribute("asztalEtteremTermekek", etteremTermekService.findAll());
        return "etterem/termek_fooldal";
    }
}
