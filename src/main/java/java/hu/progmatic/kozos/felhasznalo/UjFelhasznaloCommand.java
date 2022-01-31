package java.hu.progmatic.kozos.felhasznalo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UjFelhasznaloCommand {
  private String nev;
  private String jelszo;
  private UserType role;
}
