package com.wodder.product.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReceiveShipmentCommand {
  private String shipmentId;
  private List<Item> items = new ArrayList<>();

  public String getShipmentId() {
    return shipmentId;
  }

  public void setShipmentId(String shipmentId) {
    this.shipmentId = shipmentId;
  }

  public Iterator<Item> getItems() {
    return items.iterator();
  }

  public void addItem(Item i) {
    items.add(i);
  }

  public static class Item {
    private String id;
    private String name;
    private String units;
    private String numberOfItems;
    private String numberOfCases;
    private String itemPrice;
    private String casePrice;

    private String itemsPerCase;

    public String getId() {
      return id;
    }

    public Item setId(String id) {
      this.id = id;
      return this;
    }

    public String getName() {
      return name;
    }

    public Item setName(String name) {
      this.name = name;
      return this;
    }

    public String getUnits() {
      return units;
    }

    public Item setUnits(String units) {
      this.units = units;
      return this;
    }

    public String getNumberOfItems() {
      return numberOfItems;
    }

    public Item setNumberOfItems(String numberOfItems) {
      this.numberOfItems = numberOfItems;
      return this;
    }

    public String getNumberOfCases() {
      return numberOfCases;
    }

    public Item setNumberOfCases(String numberOfCases) {
      this.numberOfCases = numberOfCases;
      return this;
    }

    public String getItemPrice() {
      return itemPrice;
    }

    public Item setItemPrice(String itemPrice) {
      this.itemPrice = itemPrice;
      return this;
    }

    public String getCasePrice() {
      return casePrice;
    }

    public String getItemsPerCase() {
      return itemsPerCase;
    }

    public Item setItemsPerCase(String itemsPerCase) {
      this.itemsPerCase = itemsPerCase;
      return this;
    }

    public Item setCasePrice(String casePrice) {
      this.casePrice = casePrice;
      return this;
    }
  }
}
