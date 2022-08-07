package com.wodder.product.dto;

import java.io.Serializable;
import java.util.Map;

public class ProductDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String name;
  private String category;
  private String units;
  private String itemPrice;
  private String casePrice;
  private boolean active;
  private int unitsPerCase;
  private String externalId;
  private String qtyOnHand;

  private ProductDto(ProductModelBuilder b) {
    this.id = b.id;
    this.name = b.name;
    this.category = b.category;
    this.active = b.active;
    this.units = b.units;
    this.unitsPerCase = b.itemsPerCase;
    this.itemPrice = b.itemPrice;
    this.casePrice = b.casePrice;
    this.externalId = b.externalId;
    this.qtyOnHand = b.qtyOnHand;
  }

  public ProductDto(ProductDto that) {
    this.id = that.id;
    this.name = that.name;
    this.category = that.category;
    this.active = that.active;
    this.units = that.units;
    this.unitsPerCase = that.unitsPerCase;
    this.itemPrice = that.itemPrice;
    this.casePrice = that.casePrice;
    this.externalId = that.externalId;
  }

  public ProductDto() {
  }

  public static ProductDto fromMap(Map<String, String> values) {
    ProductDto result = new ProductDto();
    if (values != null && !values.isEmpty()) {
      result.id = values.get("ID");
      result.name = values.get("NAME");
      result.category = values.get("CATEGORY");
    }
    return result;
  }

  public static ProductModelBuilder builder() {
    return new ProductModelBuilder();
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

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public int getUnitsPerCase() {
    return unitsPerCase;
  }

  public void setUnitsPerCase(int itemsPerCase) {
    this.unitsPerCase = itemsPerCase;
  }

  public String getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(String itemPrice) {
    this.itemPrice = itemPrice;
  }

  public String getCasePrice() {
    return casePrice;
  }

  public void setCasePrice(String casePrice) {
    this.casePrice = casePrice;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public String getQtyOnHand() {
    return qtyOnHand;
  }

  public void setQtyOnHand(String qtyOnHand) {
    this.qtyOnHand = qtyOnHand;
  }

  public static class ProductModelBuilder {
    private String id;
    private String name;
    private String category;
    private String units;
    private String itemPrice;
    private String casePrice;
    private int itemsPerCase;
    private boolean active;
    private String externalId;
    private String qtyOnHand;

    private ProductModelBuilder() {
      // no-op
    }

    public ProductModelBuilder withId(String id) {
      this.id = id;
      return this;
    }

    public ProductModelBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ProductModelBuilder withCategory(String category) {
      this.category = category;
      return this;
    }

    public ProductModelBuilder withUnitOfMeasurement(String uom) {
      this.units = uom;
      return this;
    }

    public ProductModelBuilder withItemsPerCase(int itemsPerCase) {
      this.itemsPerCase = itemsPerCase;
      return this;
    }

    public ProductModelBuilder withItemPrice(String itemPrice) {
      this.itemPrice = itemPrice;
      return this;
    }

    public ProductModelBuilder withCasePrice(String casePrice) {
      this.casePrice = casePrice;
      return this;
    }

    public ProductModelBuilder isActive(boolean active) {
      this.active = active;
      return this;
    }

    public ProductModelBuilder withExternalId(String externalId) {
      this.externalId = externalId;
      return this;
    }

    public ProductModelBuilder withQuantityOnHand(String qtyOnHand) {
      this.qtyOnHand = qtyOnHand;
      return this;
    }

    public ProductDto build() {
      return new ProductDto(this);
    }
  }
}
