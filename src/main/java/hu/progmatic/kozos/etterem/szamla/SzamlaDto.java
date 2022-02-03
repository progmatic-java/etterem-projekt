package hu.progmatic.kozos.etterem.szamla;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SzamlaDto implements Serializable{
    private Integer id;
    private Integer asztalId;
    private List<SzamlaTetelDto> szamlaTetelek;
}
