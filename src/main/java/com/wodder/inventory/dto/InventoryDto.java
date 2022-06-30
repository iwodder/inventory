package com.wodder.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryDto {

  @JsonProperty
  private List<CountDto> items;
  @JsonProperty
  private String state;
  @JsonProperty
  private String id;
  @JsonProperty
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate inventoryDate;

  public InventoryDto(String id, String state) {
    this.id = id;
    this.state = state;
    items = new ArrayList<>();
  }

  public InventoryDto() {
    this("", "OPEN");
  }

  public void addCountDto(CountDto countDto) {
    items.add(countDto);
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

  public List<CountDto> getItems() {
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
