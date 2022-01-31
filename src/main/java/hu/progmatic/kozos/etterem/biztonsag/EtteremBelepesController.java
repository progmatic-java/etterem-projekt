package hu.progmatic.kozos.etterem.biztonsag;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EtteremBelepesController {
    @RequestMapping("/etterem/belepes")
    public String login() {
        return "etterem/belepes";
    }
}