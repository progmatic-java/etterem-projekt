package java.hu.progmatic.kozos.etterem.biztonsag;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EtteremLoginController {
    @RequestMapping("/etteremlogin")
    public String login() {
        return "etteremlogin";
    }
}