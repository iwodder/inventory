package com.wodder.product.domain.model.product;

import java.util.Objects;
import java.util.UUID;

public class CategoryId {

  private final String id;

  CategoryId() {
    id = UUID.randomUUID().toString();
  }

  private CategoryId(String id) {
    this.id = id;
  }

  public static CategoryId generateId() {
    return new CategoryId();
  }

  public static CategoryId categoryIdOf(String value) {
    return new CategoryId(value);
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CategoryId)) {
      return false;
    }
    CategoryId that = (CategoryId) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
