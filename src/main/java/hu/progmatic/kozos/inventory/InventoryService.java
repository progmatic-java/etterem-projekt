package hu.progmatic.kozos.inventory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class InventoryService implements InitializingBean {
  private final List<InventoryItem> initItems = List.of(
      new InventoryItem(null, "Arany", 1, 1, 0, 0),
      new InventoryItem(null, "Kard", 1, 2, 5, 0),
      new InventoryItem(null, "Buzogány", 1, 3, 8, 0),
      new InventoryItem(null, "Pajzs", 1, 2, 1, 10),
      new InventoryItem(null, "Sör", 1, 1, 0, 0)
  );

  @Autowired
  private InventoryItemRepository inventoryItemRepository;

  public List<InventoryItem> findAll() {
    return inventoryItemRepository.findAll();
  }

  public InventoryItem create(InventoryItem item) {
    item.setId(null);
    return inventoryItemRepository.save(item);
  }

  public InventoryItem save(InventoryItem item) {
    InventoryItem managedEntity = getById(item.getId());
    managedEntity.setMegnevezes(item.getMegnevezes());
    managedEntity.setAr(item.getAr());
    managedEntity.setSebzes(item.getSebzes());
    managedEntity.setSuly(item.getSuly());
    managedEntity.setPajzsErtek(item.getPajzsErtek());

    return managedEntity;
  }

  public void deleteById(Integer id) {
    inventoryItemRepository.deleteById(id);
  }

  public InventoryItem getById(Integer id) {
    return inventoryItemRepository.getById(id);
  }

  public InventoryItem empty() {
    return new InventoryItem();
  }

  @Override
  public void afterPropertiesSet() {
    if (inventoryItemRepository.count() == 0) {
      inventoryItemRepository.saveAll(initItems);
    }
  }

  public List<InventoryItemViewDto> findAllDto() {
    return findAll().stream()
        .map(this::buildDto)
        .toList();
  }

  public InventoryItemViewDto getDtoById(Integer id) {
    return buildDto(getById(id));
  }

  private InventoryItemViewDto buildDto(InventoryItem inventoryItem) {
    return InventoryItemViewDto.builder()
        .ar(inventoryItem.getAr())
        .megnevezes(inventoryItem.getMegnevezes())
        .pajzsErtek(inventoryItem.getPajzsErtek())
        .sebzes(inventoryItem.getSebzes())
        .suly(inventoryItem.getSuly())
        .build();
  }
}
