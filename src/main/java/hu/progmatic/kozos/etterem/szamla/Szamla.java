package hu.progmatic.kozos.etterem.szamla;


import lombok.*;

import javax.persistence.*;
import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Szamla {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Asztal asztal;
}
