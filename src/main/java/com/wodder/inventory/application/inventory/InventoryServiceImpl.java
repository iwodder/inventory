package com.wodder.inventory.application.inventory;

import com.wodder.inventory.domain.model.inventory.Count;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.InventoryId;
import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.ItemId;
import com.wodder.inventory.dto.CountDto;
import com.wodder.inventory.dto.InventoryDto;
import com.wodder.inventory.persistence.InventoryRepository;
import com.wodder.inventory.persistence.Repository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository repository;
  private final Repository<Item, ItemId> itemRepository;

  public InventoryServiceImpl(
      InventoryRepository repository,
      Repository<Item, ItemId> itemRepository) {
    this.repository = repository;
    this.itemRepository = itemRepository;
  }

  @Override
  public Inventory createInventory() {
    List<Item> items = itemRepository.loadAllItems();
    Inventory inv = Inventory.inventoryWith(items);

    return repository.createItem(inv);
  }

  @Override
  public Inventory createEmptyInventory() {
    return repository.createItem(Inventory.emptyInventory());
  }

  @Override
  public Optional<Inventory> changeInventoryCount(
      String inventoryId,
      String itemId,
      String units,
      String cases) {
    Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
    Optional<Item> itemOpt = itemRepository.loadById(ItemId.of(itemId));
    if (opt.isPresent() && itemOpt.isPresent()) {
      Inventory i = opt.get();
      Item item = itemOpt.get();
      i.updateCountFor(item, Count.countOf(units, cases));
      return repository.updateItem(i);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Inventory> updateInventoryCounts(
      String inventoryId,
      Collection<CountDto> counts) {
    Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
    if (opt.isPresent()) {
      Inventory i = opt.get();
      for (var count : counts) {
        Optional<Item> itemOptional = itemRepository.loadById(ItemId.of(count.getProductId()));
        if (itemOptional.isPresent()) {
          i.updateCountFor(itemOptional.get(), Count.countOf(count.getUnits(), count.getCases()));
        }
      }
      return Optional.of(i);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void saveInventory(InventoryDto model) {
    Inventory i = repository.loadById(InventoryId.inventoryIdOf(model.getId())).get();
    repository.updateItem(i);
  }

  @Override
  public Optional<Inventory> loadInventory(String inventoryId) {
    return repository.loadById(InventoryId.inventoryIdOf(inventoryId));
  }

  @Override
  public Optional<Count> getCount(String inventoryId, String itemId) {
    Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
    Optional<Item> opt2 = itemRepository.loadById(ItemId.of(itemId));

    if (opt.isPresent() && opt2.isPresent()) {
      Inventory inv = opt.get();
      Item item = opt2.get();
      return inv.getCount(item.getName(), item.getLocation());
    } else {
      return Optional.empty();
    }
  }
}
