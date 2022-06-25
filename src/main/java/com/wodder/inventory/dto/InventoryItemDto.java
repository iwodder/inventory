package com.wodder.inventory.dto;

import com.wodder.inventory.domain.model.inventory.Item;

public class InventoryItemDto {

  private String id;
  private String productId;
  private String name;
  private String location;
  private String units;
  private String itemsPerCase;

  public InventoryItemDto(
      String id,
      String name,
      String location,
      String units,
      String itemsPerCase) {
    this.name = name;
    this.location = location;
    this.units = units;
    this.itemsPerCase = itemsPerCase;
  }

  public InventoryItemDto(
      String id,
      String productId,
      String name,
      String location,
      String units,
      String itemsPerCase) {
    this.id = id;
    this.productId = productId;
    this.name = name;
    this.location = location;
    this.units = units;
    this.itemsPerCase = itemsPerCase;
  }

  public InventoryItemDto(Item item) {
    this(
        item.getId().getValue(),
        item.getProductId(),
        item.getName(),
        item.getLocation().getName(),
        item.getUom().getUnit(),
        Integer.toString(item.getUom().getItemsPerCase()));
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public String getItemsPerCase() {
    return itemsPerCase;
  }

  public void setItemsPerCase(String itemsPerCase) {
    this.itemsPerCase = itemsPerCase;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
}
