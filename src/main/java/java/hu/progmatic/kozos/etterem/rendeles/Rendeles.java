package java.hu.progmatic.kozos.etterem.rendeles;


import lombok.*;

import javax.persistence.*;
import java.hu.progmatic.kozos.etterem.asztal.Asztal;
import java.hu.progmatic.kozos.etterem.leltar.EtteremTermek;

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
    private Integer mennyiseg;
    @ManyToOne
    private Asztal asztal;

}
