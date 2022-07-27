package com.wodder.product.domain.model.product;

import java.util.Objects;
import java.util.UUID;

public class LocationId {

  private final String id;

  LocationId() {
    id = UUID.randomUUID().toString();
  }

  private LocationId(String id) {
    this.id = id;
  }

  public static LocationId generateId() {
    return new LocationId();
  }

  public static LocationId of(String value) {
    return new LocationId(value);
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LocationId)) {
      return false;
    }
    LocationId that = (LocationId) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
