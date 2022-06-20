package com.wodder.inventory.domain.model.inventory;

import java.util.Objects;
import java.util.regex.Pattern;

public class StorageLocation {
  private static final Pattern LETTERS_AND_SPACES = Pattern.compile("[\\p{Alpha} ]+");
  private final String name;

  private StorageLocation(String name) {
    this.name = name;
  }

  public static StorageLocation of(String name) {
    validateName(name);
    return new StorageLocation(name);
  }

  public static StorageLocation unassigned() {
    return new StorageLocation("UNASSIGNED");
  }

  private static void validateName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name must not be null");
    }
    if (name.isBlank()) {
      throw new IllegalArgumentException("Name must not be blank");
    }
    if (!LETTERS_AND_SPACES.matcher(name).matches()) {
      throw new IllegalArgumentException("Only ALPHABETIC characters and spaces are accepted");
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
    StorageLocation that = (StorageLocation) o;
    return name.equalsIgnoreCase(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
