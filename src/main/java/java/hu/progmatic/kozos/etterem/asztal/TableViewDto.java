package java.hu.progmatic.kozos.etterem.asztal;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class TableViewDto implements Serializable {
  private final Integer id;
  @NotEmpty
  private final String nev;
  private final List<RendelesDto> rendelesDtoList;

  @Builder
  @Data
  public static class RendelesDto implements Serializable {
    private final Integer rendelesId;
    private final String etteremTermekNev;
    private final Integer mennyiseg;
  }
}
