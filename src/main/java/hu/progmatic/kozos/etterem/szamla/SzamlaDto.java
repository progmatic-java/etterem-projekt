package hu.progmatic.kozos.etterem.szamla;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class SzamlaDto implements Serializable{
    private Integer id;
    private Integer asztalId;
    private Integer vegosszeg;
    private Integer fizetettVegosszeg;
    private boolean split;
    private List<SzamlaTetelDto> tetelek;
    private Integer asztalSzam;
}
