package com.wodder.product.domain.model.product;

import java.util.Objects;

public class ExternalId {

  private final String id;

  private ExternalId(String id) {
    this.id = id;
  }

  public static ExternalId of(String value) {
    return new ExternalId(value);
  }

  public String getValue() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ExternalId)) {
      return false;
    }
    ExternalId that = (ExternalId) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
