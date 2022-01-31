package hu.progmatic.kozos.etterem.biztonsag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtteremUjFelhasznaloCommand {
    private String nev;
    private String jelszo;
    private EtteremFelhasznaloTipus jogosultsag;
}
