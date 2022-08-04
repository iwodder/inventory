package com.wodder.product.domain.model.shipment;

import java.util.Objects;
import java.util.UUID;

public class ShipmentId {
  private final String id;

  ShipmentId() {
    id = UUID.randomUUID().toString();
  }

  private ShipmentId(String id) {
    this.id = id;
  }

  public static ShipmentId generateId() {
    return new ShipmentId();
  }

  public static ShipmentId of(String value) {
    return new ShipmentId(value);
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ShipmentId)) {
      return false;
    }
    ShipmentId that = (ShipmentId) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
