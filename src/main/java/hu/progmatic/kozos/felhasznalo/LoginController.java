package hu.progmatic.kozos.felhasznalo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "etterem/belepes";
    }

    @ModelAttribute("felhasznaloGombList")
    public List<Felhasznalo> felhasznalok() {
        return List.of();
    }
}
