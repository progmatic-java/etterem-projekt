package java.hu.progmatic.kozos.inventory;

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
public class InventoryItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotEmpty(message = "Item megnevezése nem lehet üres")
  @Column(length = 1000)
  private String megnevezes;
  @Min(0)
  @NotNull
  private Integer ar;
  @NotNull
  @Min(0)
  private Integer suly;
  @NotNull
  private Integer sebzes;
  @NotNull
  private Integer pajzsErtek;
}
