package com.wodder.inventory.domain.model.inventory;

import java.util.Objects;
import java.util.regex.Pattern;

public class InventoryLocation {
  private static final Pattern ALPHABETIC = Pattern.compile("\\p{Alpha}+");
  private final String name;

  private InventoryLocation(String name) {
    this.name = name;
  }

  public static InventoryLocation of(String name) {
    validateName(name);
    return new InventoryLocation(name);
  }

  public static InventoryLocation defaultCategory() {
    return new InventoryLocation("UNASSIGNED");
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
    InventoryLocation that = (InventoryLocation) o;
    return name.equalsIgnoreCase(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
