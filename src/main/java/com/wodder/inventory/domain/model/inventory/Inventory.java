package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.dto.InventoryDto;
import com.wodder.inventory.dto.ProductDto;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Inventory extends Entity<InventoryId> {
  private final LocalDate date;
  private final Map<Integer, Item> counts = new HashMap<>();
  private final Map<ItemId, InventoryCount> countMap = new HashMap<>();

  private InventoryState state;

  private Inventory() {
    this(LocalDate.now());
  }

  public Inventory(LocalDate date) {
    this(InventoryId.newId(), date, InventoryState.OPEN);
  }

  private Inventory(InventoryId id, LocalDate date, InventoryState state) {
    super(id);
    this.date = date;
    this.state = state;
  }

  public Inventory(InventoryDto model) {
    this(
        InventoryId.inventoryIdOf(model.getId()),
        model.getInventoryDate(),
        InventoryState.valueOf(model.getState()));
    model.getItems().stream()
        .map(Item::fromModel)
        .forEach(
            m -> {
              counts.putIfAbsent(generateKey(m), m);
            });
  }

  public Inventory(Inventory that) {
    this(that.id, that.date, that.state);
    that.counts.forEach((k, v) -> this.counts.put(k, new Item(v)));
  }

  // This is deprecated in favor of creating an inventory with just ItemIds
  public static Inventory createNewInventoryWithProducts(Collection<ProductDto> products) {
    Inventory i = new Inventory();
    products.forEach(i::addProductToInventory);
    return i;
  }

  public static Inventory createNewInventory(Collection<ItemId> items) {
    Inventory inventory = new Inventory();
    items.forEach((id) -> {
      inventory.state.addItemToInventory(inventory.countMap, id, InventoryCount.countFor(id));
    });
    return inventory;
  }

  public static Inventory emptyInventory() {
    return new Inventory();
  }


  public void updateInventoryCount(String name, String location, InventoryCount count) {
    state.updateCount(counts, name, location, count);
  }

  public void addItemToInventory(Item item) {
    state.addItemToInventory(counts, generateKey(item), item);
  }

  private void addProductToInventory(ProductDto product) {
    Item item = Item.fromProductDto(product);
    addItemToInventory(item);
  }

  private int generateKey(Item item) {
    return Objects.hash(item.getName().toLowerCase(), item.getLocation().getName().toLowerCase());
  }

  public List<Item> getItemsByLocation(InventoryLocation location) {
    return getItemsBy(i -> i.getLocation().equals(location));
  }

  public List<Item> getItemsByCategory(InventoryCategory category) {
    return getItemsBy(i -> i.getCategory().equals(category));
  }

  public Item getItem(String s) {
    List<Item> item = getItemsBy(i -> i.getName().equalsIgnoreCase(s));
    if (item.size() == 1) {
      return item.get(0);
    } else {
      return Item.empty();
    }
  }

  private List<Item> getItemsBy(Predicate<Item> p) {
    return counts.values().stream().filter(p).collect(Collectors.toUnmodifiableList());
  }

  public int numberOfItems() {
    return counts.values().isEmpty() ? countMap.size() : counts.values().size();
  }

  public Optional<InventoryCount> getCount(String name) {
    return counts.values().stream()
        .filter(entry -> entry.getName().equalsIgnoreCase(name))
        .map(Item::getCount)
        .findAny();
  }

  public LocalDate getInventoryDate() {
    return date;
  }

  public boolean isOpen() {
    return state.isOpen();
  }

  public void finish() {
    this.state = InventoryState.CLOSED;
  }

  public InventoryDto toModel() {
    InventoryDto result = new InventoryDto(id.getId(), state.name());
    result.setInventoryDate(date);
    counts.values().stream().map(Item::toModel).forEach(result::addInventoryItem);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Inventory)) {
      return false;
    }
    Inventory inventory = (Inventory) o;
    return Objects.equals(date, inventory.date) && Objects.equals(id, inventory.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date);
  }

  public Iterable<Item> getItems() {
    return counts.values();
  }

  private enum InventoryState {
    OPEN {
      @Override
      boolean isOpen() {
        return true;
      }

      @Override
      void updateCount(
          Map<Integer, Item> counts, String name, String location, InventoryCount count) {
        int key = Objects.hash(name.toLowerCase(), location.toLowerCase());
        counts.computeIfPresent(key, (k, v) -> v.updateCount(count));
      }

      @Override
      void addItemToInventory(Map<Integer, Item> counts, Integer id, Item item) {
        counts.putIfAbsent(id, item);
      }

      @Override
      void addItemToInventory(Map<ItemId, InventoryCount> counts, ItemId id, InventoryCount count) {
        counts.putIfAbsent(id, count);
      }
    },
    CLOSED {

      @Override
      boolean isOpen() {
        return false;
      }

      @Override
      void updateCount(
          Map<Integer, Item> counts, String name, String location, InventoryCount count) {
        throw new IllegalStateException("Cannot update counts on a closed inventory");
      }

      @Override
      void addItemToInventory(Map<Integer, Item> counts, Integer id, Item item) {
        throw new IllegalStateException("Cannot add a new item to a closed inventory");
      }

      @Override
      void addItemToInventory(Map<ItemId, InventoryCount> counts, ItemId id, InventoryCount count) {
        throw new IllegalStateException("Cannot add a new item to a closed inventory");
      }
    };

    abstract boolean isOpen();

    abstract void updateCount(
        Map<Integer, Item> counts, String name, String location, InventoryCount count);

    abstract void addItemToInventory(
        Map<Integer, Item> counts, Integer id, Item item);

    abstract void addItemToInventory(
        Map<ItemId, InventoryCount> counts, ItemId id, InventoryCount count
    );
  }
}
