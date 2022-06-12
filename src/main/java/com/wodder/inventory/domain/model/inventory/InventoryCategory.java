package com.wodder.inventory.domain.model.inventory;

import java.util.Objects;
import java.util.regex.Pattern;

public class InventoryCategory {
  private static final Pattern ALPHABETIC = Pattern.compile("\\p{Alpha}+");
  private final String name;

  private InventoryCategory(String name) {
    this.name = name;
  }

  public static InventoryCategory of(String name) {
    validateName(name);
    return new InventoryCategory(name);
  }

  public static InventoryCategory defaultCategory() {
    return new InventoryCategory("UNASSIGNED");
  }

  private static void validateName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name must not be null");
    }
    if (name.isBlank()) {
      throw new IllegalArgumentException("Name must not be blank");
    }
    if (!ALPHABETIC.matcher(name).matches()) {
      throw new IllegalArgumentException("Only ALPHABETIC characters are accepted");
    }
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InventoryCategory that = (InventoryCategory) o;
    return name.equalsIgnoreCase(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
