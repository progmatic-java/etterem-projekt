package hu.progmatic.kozos.felhasznalo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private FelhasznaloService felhasznaloService;

    @RequestMapping("/login")
    public String login() {
        return "etterem/belepes";
    }

    @PostMapping("/login/felhasznalobetoltese/{id}")
    public String felhasznaloBetoltese(
            @PathVariable Long id,
            Model model
    ) {
        Felhasznalo felhasznalo = felhasznaloService.getById(id);
        model.addAttribute("formFelhasznalo", felhasznalo);
        return "redirect:/etterem/belepes";
    }

    @ModelAttribute("formFelhasznalo")
    public Felhasznalo formFelhasznalo() {
        return Felhasznalo.builder().build();
    }

    @ModelAttribute("felhasznaloGombList")
    public List<Felhasznalo> felhasznalok() {
        return felhasznaloService.findAll();
    }
}
