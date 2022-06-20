package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import com.wodder.inventory.dto.InventoryItemDto;
import com.wodder.inventory.dto.ProductDto;
import java.util.Objects;

public class Item extends Entity<ItemId> {

  private static final Item EMPTY = new Item(
      ItemId.emptyId(),
      "EMPTY",
      StorageLocation.unassigned(),
      UnitOfMeasurement.empty());

  private final String name;
  private StorageLocation location;

  private final UnitOfMeasurement uom;

  public Item(
      ItemId id,
      String name,
      StorageLocation location,
      UnitOfMeasurement uom) {
    super(id);
    this.name = name;
    this.location = location;
    this.uom = uom;
  }

  public Item(
      String name,
      StorageLocation location,
      UnitOfMeasurement uom) {
    this(ItemId.newId(), name, location, uom);
  }

  public Item(Item that) {
    this(
        that.id,
        that.name,
        that.location,
        new UnitOfMeasurement(that.uom));
  }

  private Item(ItemBuilder b) {
    super(b.id);
    this.name = b.name;
    this.location = b.location;
    this.uom = b.uom;
  }

  public static Item copy(Item other) {
    return new Item(other);
  }

  public static Item from(ProductDto p) {
    return Item.builder()
        .withName(p.getName())
        .withLocation(p.getLocation())
        .withUnits(p.getUnits(), Integer.toString(p.getUnitsPerCase()))
        .build();
  }

  public static Item from(InventoryItemDto model) {
    return new Item(
        ItemId.of(model.getId()),
        model.getName(),
        StorageLocation.of(model.getLocation()),
        new UnitOfMeasurement(model.getUnits(), Integer.parseInt(model.getItemsPerCase()))
    );
  }

  public static Item empty() {
    return EMPTY;
  }

  public Item updateCount(Count count) {
    return new Item(
        this.id,
        this.name,
        this.location,
        this.uom);
  }

  public ItemId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public StorageLocation getLocation() {
    return location;
  }

  public UnitOfMeasurement getUom() {
    return uom;
  }

  public OnHand getOnHand() {
    return OnHand.from(Count.ofZero(), Price.ofZero(), uom);
  }

  public InventoryItemDto toModel() {
    return new InventoryItemDto(this);
  }

  public static ItemBuilder builder() {
    return new ItemBuilder();
  }

  public void updateLocation(StorageLocation newLocation) {
    this.location = newLocation;
  }

  public static class ItemBuilder {

    private ItemId id;
    private String name;
    private StorageLocation location;
    private UnitOfMeasurement uom;

    private ItemBuilder() {
    }

    public ItemBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ItemBuilder withLocation(String location) {
      this.location = StorageLocation.of(location);
      return this;
    }

    public ItemBuilder withUnits(String units, String qtyPerCase) {
      this.uom = UnitOfMeasurement.of(units, qtyPerCase);
      return this;
    }

    public ItemBuilder withUnits(String units) {
      this.uom = UnitOfMeasurement.of(units, "0");
      return this;
    }

    public ItemBuilder withId(String id) {
      this.id = ItemId.of(id);
      return this;
    }

    public Item build() {
      if (id == null) {
        id = ItemId.newId();
      }
      return new Item(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Item)) {
      return false;
    }
    Item item = (Item) o;
    return this.id.equals(item.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getLocation());
  }
}
