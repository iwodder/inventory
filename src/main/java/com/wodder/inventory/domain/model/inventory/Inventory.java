package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.Product;
import com.wodder.inventory.dto.InventoryDto;
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
  private final Map<Integer, InventoryItem> counts = new HashMap<>();

  private InventoryState state;

  public Inventory() {
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
        .map(InventoryItem::fromModel)
        .forEach(
            m -> {
              counts.putIfAbsent(generateKey(m), m);
            });
  }

  public Inventory(Inventory that) {
    this(that.id, that.date, that.state);
    that.counts.forEach((k, v) -> this.counts.put(k, new InventoryItem(v)));
  }

  public static Inventory createNewInventoryWithProducts(Collection<Product> products) {
    Inventory i = new Inventory();
    products.forEach(i::addProductToInventory);
    return i;
  }

  public void updateInventoryCount(String name, String location, InventoryCount count) {
    state.updateCount(counts, name, location, count);
  }

  public void addItemToInventory(InventoryItem item) {
    state.addItemToInventory(counts, generateKey(item), item);
  }

  private void addProductToInventory(Product product) {
    InventoryItem item = InventoryItem.fromProduct(product);
    counts.put(generateKey(item), item);
  }

  private int generateKey(InventoryItem item) {
    return Objects.hash(item.getName().toLowerCase(), item.getLocation().toLowerCase());
  }

  public List<InventoryItem> getItemsByLocation(String location) {
    return getItemsBy(i -> i.getLocation().equalsIgnoreCase(location));
  }

  public List<InventoryItem> getItemsByCategory(Category category) {
    return getItemsBy(i -> i.getCategory().equalsIgnoreCase(category.getName()));
  }

  public InventoryItem getItem(String s) {
    List<InventoryItem> item = getItemsBy(i -> i.getName().equalsIgnoreCase(s));
    if (item.size() == 1) {
      return item.get(0);
    } else {
      return null;
    }
  }

  private List<InventoryItem> getItemsBy(Predicate<InventoryItem> p) {
    return counts.values().stream().filter(p).collect(Collectors.toUnmodifiableList());
  }

  public int numberOfItems() {
    return counts.values().size();
  }

  public Optional<InventoryCount> getCount(String name) {
    return counts.values().stream()
        .filter(entry -> entry.getName().equalsIgnoreCase(name))
        .map(InventoryItem::getCount)
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
    counts.values().stream().map(InventoryItem::toModel).forEach(result::addInventoryItem);
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

  private enum InventoryState {
    OPEN {
      @Override
      boolean isOpen() {
        return true;
      }

      @Override
      void updateCount(
          Map<Integer, InventoryItem> counts, String name, String location, InventoryCount count) {
        int key = Objects.hash(name.toLowerCase(), location.toLowerCase());
        counts.computeIfPresent(key, (k, v) -> v.updateCount(count));
      }

      @Override
      void addItemToInventory(Map<Integer, InventoryItem> counts, Integer id, InventoryItem item) {
        counts.putIfAbsent(id, item);
      }
    },
    CLOSED {

      @Override
      boolean isOpen() {
        return false;
      }

      @Override
      void updateCount(
          Map<Integer, InventoryItem> counts, String name, String location, InventoryCount count) {
        throw new IllegalStateException("Cannot update counts on a closed inventory");
      }

      @Override
      void addItemToInventory(Map<Integer, InventoryItem> counts, Integer id, InventoryItem item) {
        throw new IllegalStateException("Cannot add a new item to a closed inventory");
      }
    };

    abstract boolean isOpen();

    abstract void updateCount(
        Map<Integer, InventoryItem> counts, String name, String location, InventoryCount count);

    abstract void addItemToInventory(
        Map<Integer, InventoryItem> counts, Integer id, InventoryItem item);
  }
}
