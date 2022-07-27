package com.wodder.inventory.domain.model.inventory.report;

import com.wodder.inventory.domain.model.inventory.ItemId;
import java.util.Objects;

public class ReceivedItem {
  private final ItemId id;
  private final Integer quantity;

  private ReceivedItem(ItemId id, Integer quantity) {
    this.id = id;
    this.quantity = quantity;
  }

  public static ReceivedItem of(ItemId id, Integer quantity) {
    return new ReceivedItem(id, quantity);
  }

  public ItemId getId() {
    return id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReceivedItem that = (ReceivedItem) o;
    return id.equals(that.id) && quantity.equals(that.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, quantity);
  }
}
