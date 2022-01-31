package java.hu.progmatic.kozos.etterem.leltar;

import hu.progmatic.kozos.etterem.rendeles.CreateRendelesCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TipusController {
    @Autowired
    EtteremTermekService etteremTermekService;

    @GetMapping("/etterem/tipus")
    public String items(Model model) {
        return items();
    }

    private String items() {
        return "etterem/tipus";
    }

    @GetMapping("/etterem/asztal/{asztalId}/etel/{tipus}")
    public String dishes(
        @PathVariable Integer asztalId,
        @PathVariable Tipus tipus,
        Model model
    ) {
        model.addAttribute("asztalId", asztalId);
        model.addAttribute(
                "filteredByTipus",
                etteremTermekService.findAllByTipus(tipus)
        );
        return items();
    }

    @GetMapping("/etterem/asztal/{asztalId}/ital/{tipus}")
    public String drinks(
        @PathVariable Integer asztalId,
        @PathVariable Tipus tipus,
        Model model
    ) {
        model.addAttribute("asztalId", asztalId);
        model.addAttribute(
            "filteredByTipus",
            etteremTermekService.findAllByTipus(tipus)
        );
        return items();
    }

    @PostMapping("/etterem/tipus/{id}")
    public String create(
            @ModelAttribute("formItem") @Valid EtteremTermek formItem,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            etteremTermekService.create(formItem);
            refreshAllItems(model);
            clearFormItems(model);
        }
        return items();
    }

    @ModelAttribute("allItem")
    List<EtteremTermek> allItem() {
        return etteremTermekService.findAll();
    }

    @ModelAttribute("formItem")
    public EtteremTermek formItem() {
        return new EtteremTermek();
    }

    @ModelAttribute("filteredByTipus")
    List<EtteremTermek> filteredByTipus() {
        return List.of();
    }

    @ModelAttribute("termekekByTipusDto")
    List<EtteremTermekDto> termekekByTipusDto() {
        return List.of();
    }

    @ModelAttribute("createRendelesCommand")
    CreateRendelesCommand createRendelesCommand() {
        return CreateRendelesCommand.builder().build();
    }

    private void clearFormItems(Model model) {
        model.addAttribute("formItem", formItem());
    }

    private void refreshAllItems(Model model) {
        model.addAttribute("allItem", allItem());
    }


}
