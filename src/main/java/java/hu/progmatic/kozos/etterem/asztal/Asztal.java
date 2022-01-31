package java.hu.progmatic.kozos.etterem.asztal;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.hu.progmatic.kozos.etterem.rendeles.Rendeles;
import java.hu.progmatic.kozos.etterem.szamla.Szamla;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asztal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty
    private String nev;
    @OneToOne(cascade = CascadeType.ALL)
    private Szamla szamla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asztal")
    private List<Rendeles> rendelesek = new ArrayList<>();
}
