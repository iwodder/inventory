package com.wodder.inventory.dto;

import com.wodder.inventory.domain.model.inventory.InventoryItem;

public class InventoryItemDto {

  private String id;
  private String name;
  private String location;
  private String category;
  private String units;
  private String itemsPerCase;
  private String unitPrice;
  private String casePrice;
  private String numberOfUnits;
  private String numberOfCases;

  public InventoryItemDto(
      String id,
      String name,
      String location,
      String category,
      String units,
      String itemsPerCase,
      String unitPrice,
      String casePrice,
      String numberOfUnits,
      String numberOfCases) {
    this.name = name;
    this.location = location;
    this.category = category;
    this.units = units;
    this.itemsPerCase = itemsPerCase;
    this.unitPrice = unitPrice;
    this.casePrice = casePrice;
    this.numberOfUnits = numberOfUnits;
    this.numberOfCases = numberOfCases;
  }

  public InventoryItemDto(InventoryItem item) {
    this(
        item.getId().getValue(),
        item.getName(),
        item.getLocation(),
        item.getCategory(),
        item.getUom().getUnit(),
        Integer.toString(item.getUom().getItemsPerCase()),
        item.getPrice().getUnitPrice().toString(),
        item.getPrice().getUnitPrice().toString(),
        Double.toString(item.getCount().getUnits()),
        Double.toString(item.getCount().getCases()));
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public String getItemsPerCase() {
    return itemsPerCase;
  }

  public void setItemsPerCase(String itemsPerCase) {
    this.itemsPerCase = itemsPerCase;
  }

  public String getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  public String getCasePrice() {
    return casePrice;
  }

  public void setCasePrice(String casePrice) {
    this.casePrice = casePrice;
  }

  public String getNumberOfUnits() {
    return numberOfUnits;
  }

  public void setNumberOfUnits(String numberOfUnits) {
    this.numberOfUnits = numberOfUnits;
  }

  public String getNumberOfCases() {
    return numberOfCases;
  }

  public void setNumberOfCases(String numberOfCases) {
    this.numberOfCases = numberOfCases;
  }
}
