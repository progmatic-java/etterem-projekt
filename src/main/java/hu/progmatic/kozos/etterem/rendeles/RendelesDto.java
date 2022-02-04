package hu.progmatic.kozos.etterem.rendeles;

import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendelesDto {
  private Integer id;
  private EtteremTermekDto etteremTermekDto;
  private Integer mennyiseg;
}
