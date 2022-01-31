package hu.progmatic.kozos.etterem.leltar;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EtteremTermekDto implements Serializable {
  private final Integer id;
  private final String nev;
  private final Integer ar;
  private final Integer mennyiseg;
}
