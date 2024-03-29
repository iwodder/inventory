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
import javax.inject.Inject;
import javax.inject.Named;

public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository repository;
  private final Repository<Item, ItemId> itemRepository;

  @Inject
  public InventoryServiceImpl(
      InventoryRepository repository,
      @Named("InMemoryItemRepository") Repository<Item, ItemId> itemRepository) {
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
      counts.forEach(c -> updateItemCount(i, c));
      return Optional.of(i);
    } else {
      return Optional.empty();
    }
  }

  private void updateItemCount(Inventory i, CountDto c) {
    Optional<Item> opt = itemRepository.loadById(ItemId.of(c.getItemId()));
    if (opt.isPresent()) {
      Item item = opt.get();
      i.updateCountFor(item, Count.countOf(c.getUnits(), c.getCases()));
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

    if (opt.isPresent()) {
      Inventory inv = opt.get();
      return inv.getCount(ItemId.of(itemId));
    } else {
      return Optional.empty();
    }
  }
}
