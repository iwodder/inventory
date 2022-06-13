package com.wodder.inventory.domain.model.inventory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class ItemId {
  private final String id;

  private ItemId(String id) {
    this.id = id;
  }

  private ItemId() {
    this(UUID.randomUUID().toString());
  }

  public static ItemId newId() {
    return new ItemId();
  }

  public static ItemId of(String id) {
    return new ItemId(id);
  }

  public static ItemId from(String source) {
    return new ItemId(
        UUID.nameUUIDFromBytes(
            source.getBytes(StandardCharsets.UTF_8)).toString());
  }

  public static ItemId emptyId() {
    return new ItemId("");
  }

  public String getValue() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemId itemId = (ItemId) o;
    return id.equals(itemId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
