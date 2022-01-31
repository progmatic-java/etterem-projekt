package hu.progmatic.kozos.inventory;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
public class InventoryItemViewDto implements Serializable {
  @NotEmpty(message = "Item megnevezése nem lehet üres")
  private final String megnevezes;
  @Min(0)
  @NotNull
  private final Integer ar;
  @NotNull
  @Min(0)
  private final Integer suly;
  @NotNull
  private final Integer sebzes;
  @NotNull
  private final Integer pajzsErtek;
}
