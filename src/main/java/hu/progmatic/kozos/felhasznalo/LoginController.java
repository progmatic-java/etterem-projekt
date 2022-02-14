package hu.progmatic.kozos.felhasznalo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model) {
        getFelhasznaloGombLista(model);
        return "etterem/belepes";
    }

    private void getFelhasznaloGombLista(Model model) {
        List<Felhasznalo> felhasznaloList = List.of(
                new Felhasznalo(null, "admin", "adminpass", UserType.ADMIN),
                new Felhasznalo(null, "bence", "bence", UserType.ADMIN),
                new Felhasznalo(null, "benji", "benji", UserType.ADMIN),
                new Felhasznalo(null, "attila", "attila", UserType.ADMIN),
                new Felhasznalo(null, "olivér", "olivér", UserType.ADMIN),
                new Felhasznalo(null, "dávid", "dávid", UserType.ADMIN),
                new Felhasznalo(null, "miska", "miska", UserType.ADMIN),
                new Felhasznalo(null, "felszolgáló", "felszolgáló", UserType.FELSZOLGALO)
        );
        model.addAttribute("felhasznaloGombList", felhasznaloList);
    }

    @ModelAttribute("felhasznaloGombList")
    public List<Felhasznalo> felhasznalok() {
        return List.of();
    }
}
