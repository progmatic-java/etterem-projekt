package hu.progmatic.kozos.etterem.szamla;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SzamlaTetelDto implements Serializable{
    private Integer id;
    private Integer asztalId;
    private String termekNev;
    private Integer termekAr;
    private Integer mennyiseg;
    private Boolean fizetve;
}
