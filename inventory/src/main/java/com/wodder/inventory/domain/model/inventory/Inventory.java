package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.dto.CountDto;
import com.wodder.inventory.dto.InventoryDto;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Inventory extends Entity<InventoryId> {
  private final LocalDate inventoryDate;
  private final Map<Item, Count> counts = new HashMap<>();
  private InventoryState state;

  private Inventory() {
    this(LocalDate.now());
  }

  public Inventory(LocalDate inventoryDate) {
    this(InventoryId.newId(), inventoryDate, InventoryState.OPEN);
  }

  private Inventory(InventoryId id, LocalDate inventoryDate, InventoryState state) {
    super(id);
    this.inventoryDate = inventoryDate;
    this.state = state;
  }

  public Inventory(Inventory that) {
    this(that.id, that.inventoryDate, that.state);
    that.counts.forEach((k, v) ->
        this.counts.put(Item.copy(k), Count.copy(v)));
  }

  public static Inventory inventoryWith(Collection<Item> items) {
    Inventory inv = new Inventory();
    items.forEach(inv::addItemToInventory);
    return inv;
  }

  public static Inventory emptyInventory() {
    return new Inventory();
  }

  public void updateInventoryCount(String name, String location, Count count) {
    state.updateCount(counts, name, location, count);
  }

  public void updateCountFor(Item item, Count count) {
    state.updateCount(counts, item, count);
  }

  public void addItemToInventory(Item item) {
    state.addItemToInventory(counts, item);
  }

  public List<Item> getItemsByLocation(StorageLocation location) {
    return getItemsBy(i -> i.getLocation().equals(location));
  }

  public Optional<Item> getItem(String s) {
    return counts.keySet()
        .stream()
        .filter(k -> k.getName().equalsIgnoreCase(s))
        .findFirst();
  }

  private List<Item> getItemsBy(Predicate<Item> p) {
    return counts.keySet().stream().filter(p).collect(Collectors.toUnmodifiableList());
  }

  public int numberOfItems() {
    return counts.keySet().size();
  }

  public Optional<Count> getCount(ItemId id) {
    Optional<Item> key =
        counts.keySet().stream()
            .filter(item -> item.getId().equals(id))
            .findAny();
    return key.map(counts::get);
  }

  public Optional<Count> getCount(String name) {
    List<Count> c = counts.entrySet()
        .stream()
        .filter(e -> e.getKey().getName().equalsIgnoreCase(name))
        .map(Entry::getValue)
        .collect(Collectors.toList());

    if (!c.isEmpty()) {
      Count total = Count.ofZero();
      for (var count : c) {
        total = total.add(count);
      }
      return Optional.of(total);
    } else {
      return Optional.empty();
    }
  }

  public LocalDate getInventoryDate() {
    return inventoryDate;
  }

  public boolean isOpen() {
    return state.isOpen();
  }

  public void finish() {
    this.state = InventoryState.CLOSED;
  }

  public InventoryDto toModel() {
    InventoryDto result = new InventoryDto(id.getValue(), state.name());
    result.setInventoryDate(inventoryDate.format(DateTimeFormatter.ISO_DATE));
    counts.entrySet()
        .stream()
        .map(e -> {
          Item item = e.getKey();
          Count count = e.getValue();
          return new CountDto(
              item.getId().getValue(),
              item.getProductId(),
              new DecimalFormat("##0.00").format(count.getUnits()),
              new DecimalFormat("##0.00").format(count.getCases()));
        }).forEach(result::addCountDto);
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
    return
        Objects.equals(inventoryDate, inventory.inventoryDate)
            && Objects.equals(id, inventory.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inventoryDate, id);
  }

  public Iterable<Item> getItems() {
    return counts.keySet();
  }

  private enum InventoryState {
    OPEN {
      @Override
      boolean isOpen() {
        return true;
      }

      @Override
      void updateCount(
          Map<Item, Count> counts, String name, String location, Count count) {
        //counts.replace(, (k, v) -> v.updateCount(count));
      }

      @Override
      void updateCount(Map<Item, Count> counts, Item item, Count count) {
        counts.replace(item, count);
      }

      @Override
      void addItemToInventory(Map<Item, Count> counts, Item item) {
        counts.putIfAbsent(item, Count.ofZero());
      }
    },
    CLOSED {

      @Override
      boolean isOpen() {
        return false;
      }

      @Override
      void updateCount(
          Map<Item, Count> counts, String name, String location, Count count) {
        throw new IllegalStateException("Cannot update counts on a closed inventory");
      }

      @Override
      void updateCount(Map<Item, Count> counts, Item item, Count count) {
        throw new IllegalStateException("Can't update counts on a closed inventory");
      }

      @Override
      void addItemToInventory(Map<Item, Count> counts, Item item) {
        throw new IllegalStateException("Cannot add a new item to a closed inventory");
      }
    };

    abstract boolean isOpen();

    abstract void updateCount(
        Map<Item, Count> counts, String name, String location, Count count
    );

    abstract void updateCount(Map<Item, Count> counts, Item item, Count count);

    abstract void addItemToInventory(
        Map<Item, Count> counts, Item item
    );
  }
}
