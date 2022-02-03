package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SzamlaDto implements Serializable{
    private Integer id;
    private Integer asztalId;
    private List<RendelesDto> rendelesek;
}
