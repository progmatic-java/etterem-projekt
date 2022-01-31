package java.hu.progmatic.kozos.etterem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EtteremController {

  @GetMapping("/etterem")
  public String etterem() {
    return "etterem/belepes";
  }
}
