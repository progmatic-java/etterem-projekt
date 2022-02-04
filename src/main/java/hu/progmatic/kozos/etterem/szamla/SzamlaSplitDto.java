package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SzamlaSplitDto implements Serializable {
  private Integer id;
  @Builder.Default
  private List<RendelesDto> rendelesek = new ArrayList<>();
  @Builder.Default
  private Integer vegosszeg = 0;
}
