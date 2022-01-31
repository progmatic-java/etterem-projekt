package hu.progmatic.kozos.etterem.biztonsag;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class EtteremFelhasznaloController {
    private final EtteremFelhasznaloService felhasznaloService;

    public EtteremFelhasznaloController(EtteremFelhasznaloService felhasznaloService) {
        this.felhasznaloService = felhasznaloService;
    }

    @GetMapping("/etteremfelhasznalo")
    public String lista() {
        return "etteremfelhasznalo";
    }

    @PostMapping("/etteremfelhasznalo")
    public String add(@ModelAttribute EtteremUjFelhasznaloCommand command, Model model) {
        model.addAttribute("ujFelhasznaloError", null);
        try {
            felhasznaloService.add(command);
        } catch (EtteremFelhasznaloLetrehozasaException e) {
            model.addAttribute("ujFelhasznaloError", e.getMessage());
            return "felhasznalo";
        }
        frissit(model);
        return "etteremfelhasznalo";
    }

    private void frissit(Model model) {
        model.addAttribute("allFelhasznalo", populateTypes());
    }

    @PostMapping("/etteremfelhasznalo/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        felhasznaloService.delete(id);
        frissit(model);
        return "redirect:/etteremfelhasznalo";
    }

    @ModelAttribute("allFelhasznalo")
    public List<EtteremFelhasznalo> populateTypes() {
        if (felhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE)) {
            return felhasznaloService.findAll();
        }
        return List.of();
    }

    @ModelAttribute("ujFelhasznaloCommand")
    public EtteremUjFelhasznaloCommand ujFelhasznaloCommand() {
        return new EtteremUjFelhasznaloCommand();
    }

    @ModelAttribute("ujFelhasznaloError")
    public String ujFelhasznaloError() {
        return null;
    }

    @ModelAttribute("hasUserWriteRole")
    public boolean userWriteRole() {
        return felhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE);
    }

    @ModelAttribute("hasUserReadRole")
    public boolean userReadRole() {
        return felhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE);
    }

    @ModelAttribute("userId")
    public Long userId() {
        return felhasznaloService.getFelhasznaloId();
    }

    @ModelAttribute("allRole")
    public List<String> allRole() {
        return Arrays.stream(EtteremFelhasznaloTipus.values())
                .map(EtteremFelhasznaloTipus::name).toList();
    }
}
