package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.asztal.AsztalDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SzamlaDto implements Serializable{
    private Integer id;
    private AsztalDto asztalDto;
    private Integer vegosszeg;
}
