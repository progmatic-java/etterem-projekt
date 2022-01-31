package java.hu.progmatic.kozos.etterem.leltar;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtteremTermek {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty
    private String nev;
    @NotNull
    @Min(0)
    private Integer ar;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Tipus tipus;
}
