package com.wodder.inventory.application.inventory;

public class CopyItemCommand {

  private String itemId;
  private String location;

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "CopyItemCommand{"
        + "itemId='" + itemId + '\''
        + ", location='" + location + '\''
        + '}';
  }
}
