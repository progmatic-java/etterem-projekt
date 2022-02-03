package hu.progmatic.kozos.etterem.rendeles;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RendelesDto implements Serializable{
    private Integer id;
    private Integer asztalId;
    private String termekNev;
    private Integer mennyiseg;
    private Integer termekAr;
}
