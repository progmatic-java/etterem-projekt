package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.asztal.AsztalDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@Builder
public class SzamlaDto implements Serializable{
    private Integer id;
    private AsztalDto asztalDto;
    private Integer vegosszeg;
}
