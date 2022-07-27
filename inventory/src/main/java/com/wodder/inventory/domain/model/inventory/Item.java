package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.dto.InventoryItemDto;
import java.util.Objects;

public class Item extends Entity<ItemId> {

  private static final Item EMPTY = new Item(
      ItemId.emptyId(),
      "N/A",
      "EMPTY",
      StorageLocation.unassigned(),
      Unit.empty());

  private final String name;
  private final String productId;
  private StorageLocation location;
  private final Unit uom;

  public Item(
      ItemId id,
      String productId,
      String name,
      StorageLocation location,
      Unit uom) {
    super(id);
    this.name = name;
    this.productId = productId;
    this.location = location;
    this.uom = uom;
  }

  public Item(
      String name,
      StorageLocation location,
      Unit uom) {
    this(ItemId.newId(), "", name, location, uom);
  }

  public Item(Item that) {
    this(
        that.id,
        that.productId,
        that.name,
        that.location,
        that.uom);
  }

  private Item(ItemBuilder b) {
    super(b.id);
    this.name = b.name;
    this.productId = b.productId;
    this.location = b.location;
    this.uom = b.uom;
  }

  public static Item copy(Item other) {
    return new Item(other);
  }

  public static Item from(InventoryItemDto model) {
    return new Item(
        ItemId.of(model.getId()),
        model.getProductId(),
        model.getName(),
        StorageLocation.of(model.getLocation()),
        Unit.of(model.getUnits())
    );
  }

  public static Item empty() {
    return EMPTY;
  }

  public Item updateCount(Count count) {
    return new Item(
        this.id,
        this.productId,
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

  public Unit getUom() {
    return uom;
  }

  public String getProductId() {
    return productId;
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
    private String productId;
    private String name;
    private StorageLocation location;
    private Unit uom;

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
      this.uom = Unit.of(units);
      return this;
    }

    public ItemBuilder withUnits(String units) {
      this.uom = Unit.of(units);
      return this;
    }

    public ItemBuilder withId(String id) {
      this.id = ItemId.of(id);
      return this;
    }

    public ItemBuilder withProductId(String productId) {
      this.productId = productId;
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
