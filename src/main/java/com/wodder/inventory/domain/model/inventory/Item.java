package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.Product;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import com.wodder.inventory.dto.InventoryItemDto;
import java.util.Objects;

public class Item {

  private static final Item EMPTY = new Item(
      ItemId.emptyId(),
      "null_item",
      InventoryLocation.defaultCategory(),
      InventoryCategory.defaultCategory(),
      UnitOfMeasurement.empty(),
      Price.ofZero(),
      InventoryCount.ofZero());

  private final ItemId id;
  private final String name;
  private final InventoryLocation location;
  private final InventoryCategory category;
  private final UnitOfMeasurement uom;
  private final Price price;
  private final InventoryCount count;

  public Item(
      ItemId id,
      String name,
      InventoryLocation location,
      InventoryCategory category,
      UnitOfMeasurement uom,
      Price price,
      InventoryCount count) {
    this.id = id;
    this.name = name;
    this.location = location;
    this.category = category;
    this.uom = uom;
    this.price = price;
    this.count = count;
  }

  public Item(
      String name,
      InventoryLocation location,
      InventoryCategory category,
      UnitOfMeasurement uom,
      Price price) {
    this(ItemId.newId(), name, location, category, uom, price, InventoryCount.ofZero());
  }

  Item(Item that) {
    this(
        that.id,
        that.name,
        that.location,
        that.category,
        new UnitOfMeasurement(that.uom),
        new Price(that.price),
        new InventoryCount(that.count));
  }

  private Item(ItemBuilder b) {
    this.id = b.id;
    this.name = b.name;
    this.category = b.category;
    this.location = b.location;
    this.uom = b.uom;
    this.price = b.price;
    this.count = b.count;
  }

  public static Item fromProduct(Product p) {
    return new Item(
        p.getName(),
        InventoryLocation.of(p.getLocation()),
        InventoryCategory.of(p.getCategory().getName()),
        p.getUnits(),
        p.getPrice());
  }

  public static Item fromModel(InventoryItemDto model) {
    return new Item(
        ItemId.of(model.getId()),
        model.getName(),
        InventoryLocation.of(model.getLocation()),
        InventoryCategory.of(model.getCategory()),
        new UnitOfMeasurement(model.getUnits(), Integer.parseInt(model.getItemsPerCase())),
        new Price(model.getUnitPrice(), model.getCasePrice()),
        new InventoryCount(
            Double.parseDouble(model.getNumberOfUnits()),
            Double.parseDouble(model.getNumberOfCases())));
  }

  public static Item empty() {
    return EMPTY;
  }

  public Item updateCount(InventoryCount count) {
    return new Item(
        this.id,
        this.name,
        this.location,
        this.category,
        this.uom,
        this.price,
        count);
  }

  public ItemId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public InventoryLocation getLocation() {
    return location;
  }

  public InventoryCategory getCategory() {
    return category;
  }

  public UnitOfMeasurement getUom() {
    return uom;
  }

  public Price getPrice() {
    return price;
  }

  public InventoryCount getCount() {
    return count;
  }

  public OnHand getOnHand() {
    return OnHand.from(count, price, uom);
  }

  public InventoryItemDto toModel() {
    return new InventoryItemDto(this);
  }

  public static ItemBuilder builder() {
    return new ItemBuilder();
  }

  public static class ItemBuilder {

    private ItemId id;
    private String name;
    private InventoryLocation location;
    private InventoryCategory category;
    private UnitOfMeasurement uom;
    private Price price;
    private InventoryCount count;

    private ItemBuilder() {
      count = InventoryCount.ofZero();
    }

    public ItemBuilder withName(String name) {
      this.id = ItemId.from(name);
      this.name = name;
      return this;
    }

    public ItemBuilder withLocation(String location) {
      this.location = InventoryLocation.of(location);
      return this;
    }

    public ItemBuilder withCategory(String category) {
      this.category = InventoryCategory.of(category);
      return this;
    }

    public ItemBuilder withUnits(String units, String qtyPerCase) {
      this.uom = UnitOfMeasurement.of(units, qtyPerCase);
      return this;
    }

    public ItemBuilder withPricing(String unitPrice, String casePrice) {
      this.price = Price.of(unitPrice, casePrice);
      return this;
    }

    public ItemBuilder withCount(String units, String cases) {
      this.count = InventoryCount.countFrom(units, cases);
      return this;
    }

    public Item build() {
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
    return Objects.hash(getName(), getLocation(), getCategory());
  }


}
