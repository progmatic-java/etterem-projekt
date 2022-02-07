package hu.progmatic.kozos.etterem.rendeles;


import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.leltar.EtteremTermek;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rendeles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private EtteremTermek etteremTermek;
    @NotNull
    private Integer mennyiseg;
    @ManyToOne
    private Asztal asztal;
}
