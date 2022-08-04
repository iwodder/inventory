package com.wodder.product.domain.model.shipment;

public class ItemId {

  private final String id;

  private ItemId(String id) {
    this.id = id;
  }

  public static ItemId of(String id) {
    return new ItemId(id);
  }

  public String getValue() {
    return this.id;
  }


}
