package com.wodder.inventory.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private final List<InventoryItemDto> items;
  private final String state;
  private final String id;
  private LocalDate inventoryDate;

  public InventoryDto(String id, String state) {
    this.id = id;
    this.state = state;
    items = new ArrayList<>();
  }

  public InventoryDto() {
    this("", "OPEN");
  }

  public void addInventoryItem(InventoryItemDto inventoryItemDto) {
    items.add(inventoryItemDto);
  }

  public LocalDate getInventoryDate() {
    return inventoryDate;
  }

  public void setInventoryDate(LocalDate inventoryDate) {
    this.inventoryDate = inventoryDate;
  }

  public int numberOfItems() {
    return items.size();
  }

  public String getState() {
    return state;
  }

  public String getId() {
    return id;
  }

  public List<InventoryItemDto> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InventoryDto that = (InventoryDto) o;
    return getInventoryDate().equals(that.getInventoryDate())
        && getItems().equals(that.getItems())
        && getState().equals(that.getState())
        && getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getInventoryDate(), getItems(), getState(), getId());
  }
}
