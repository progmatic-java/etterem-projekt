package java.hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import lombok.*;

import javax.persistence.*;
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
    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    List<Rendeles> etteremTermekek =new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Asztal asztal;
}
