package hu.progmatic.kozos.etterem.biztonsag;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtteremFelhasznalo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nev;

    private String jelszo;

    @Enumerated(EnumType.STRING)
    private EtteremFelhasznaloTipus jogosultsag;
}