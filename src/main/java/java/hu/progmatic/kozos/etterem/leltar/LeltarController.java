package java.hu.progmatic.kozos.etterem.leltar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class LeltarController {
    @Autowired
    private EtteremTermekService etteremTermekService;

    @GetMapping("/etterem/leltar")
    public String items(Model model) {
        return items();
    }

    private String items() {
        return "/etterem/leltar";
    }

    @GetMapping("/etterem/leltar/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        EtteremTermek formItem = etteremTermekService.getById(id);
        model.addAttribute("formItem", formItem);
        return items();
    }


    @PostMapping("/etterem/leltar/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        etteremTermekService.deleteById(id);
        refreshAllTermek(model);
        return items();
    }

    @PostMapping("/etterem/leltar")
    public String create(
            @ModelAttribute("formItem") @Valid EtteremTermek formItem,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            etteremTermekService.create(formItem);
            refreshAllTermek(model);
            clearFormItem(model);
        }
        return items();
    }

    @PostMapping("/etterem/leltar/{id}")
    public String save(
            @PathVariable Integer id,
            @ModelAttribute("formItem") @Valid EtteremTermek formItem,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            etteremTermekService.save(formItem);
            refreshAllTermek(model);
            clearFormItem(model);
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

    @ModelAttribute("allTipus")
    List<Tipus> allTypes() {
        return Arrays.stream(Tipus.values()).toList();
    }

    private void clearFormItem(Model model) {
        model.addAttribute("formItem", formItem());
    }

    private void refreshAllTermek(Model model) {
        model.addAttribute("allItem", allItem());
    }
}
