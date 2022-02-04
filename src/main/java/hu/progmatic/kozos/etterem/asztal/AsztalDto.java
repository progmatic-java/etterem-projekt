package hu.progmatic.kozos.etterem.asztal;

import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsztalDto {
  private Integer id;
  private String nev;
  @Builder.Default
  private List<RendelesDto> rendelesDtoLista = new ArrayList<>();
}