package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.Product;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import com.wodder.inventory.dto.InventoryItemDto;
import java.util.Objects;

public class InventoryItem {

  private static final InventoryItem EMPTY = new InventoryItem(
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

  public InventoryItem(
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

  public InventoryItem(
      String name,
      InventoryLocation location,
      InventoryCategory category,
      UnitOfMeasurement uom,
      Price price) {
    this(ItemId.newId(), name, location, category, uom, price, InventoryCount.ofZero());
  }

  InventoryItem(InventoryItem that) {
    this(
        that.id,
        that.name,
        that.location,
        that.category,
        new UnitOfMeasurement(that.uom),
        new Price(that.price),
        new InventoryCount(that.count));
  }

  public static InventoryItem fromProduct(Product p) {
    return new InventoryItem(
        p.getName(),
        InventoryLocation.of(p.getLocation()),
        InventoryCategory.of(p.getCategory().getName()),
        p.getUnits(),
        p.getPrice());
  }

  public static InventoryItem fromModel(InventoryItemDto model) {
    return new InventoryItem(
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

  public static InventoryItem empty() {
    return EMPTY;
  }

  public InventoryItem updateCount(InventoryCount count) {
    return new InventoryItem(
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InventoryItem)) {
      return false;
    }
    InventoryItem item = (InventoryItem) o;
    return this.id.equals(item.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getLocation(), getCategory());
  }


}
