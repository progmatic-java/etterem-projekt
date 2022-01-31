package java.hu.progmatic.kozos.etterem.rendeles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.hu.progmatic.kozos.etterem.asztal.AsztalService;
import java.hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import java.util.List;


@Controller
public class RendelesController {
    @Autowired
    private RendelesService rendelesService;
    @Autowired
    private AsztalService asztalService;


    @GetMapping("/etterem/asztal/{asztalId}/rendeles")
    public String rendelesList(Model model) {
        return rendelesek();
    }

    private String rendelesek() {
        return "/etterem/termek_fooldal";
    }

    @GetMapping("/etterem/table/{asztalId}/{orderId}")
    public String etterem(@PathVariable Integer asztalId, Model model) {
        model.addAttribute("asztalId", asztalId);
        model.addAttribute("rendelesItems", rendelesItems());
        return "/etterem/rendeles";
    }

    @PostMapping("/etterem/asztal/{asztalId}/rendeles")
    public String createOrder(@PathVariable Integer asztalId,
                              @ModelAttribute("createRendelesCommand") @Valid CreateRendelesCommand command,
                              BindingResult bindingResult,
                              Model model) {
        if (!bindingResult.hasErrors()) {
            asztalId = asztalService.getIdByAsztalSzam(asztalId);
            command.setAsztalId(asztalId);
            rendelesService.create(command);
            refreshAllItem(model);
            clearFormItem(model);
        }
        return rendelesek();
    }
    
    @ModelAttribute("rendelesItems")
    List<EtteremTermekDto> rendelesItems() {
        return rendelesService.findAll();
    }

    @ModelAttribute("formItem")
    public Rendeles formItem() {
        return new Rendeles();
    }

    private void clearFormItem(Model model) {
        model.addAttribute("formItem", formItem());
    }

    private void refreshAllItem(Model model) {
        model.addAttribute("rendelesItems", rendelesItems());
    }
}
