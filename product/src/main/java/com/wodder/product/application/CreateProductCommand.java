package com.wodder.product.application;

public class CreateProductCommand {
  private String name;
  private String categoryId;
  private String unitPrice;
  private String casePrice;
  private String unitMeasurement;
  private String casePack;
  private String externalId;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getUnits() {
    return unitMeasurement;
  }

  public String getCasePack() {
    return casePack;
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

  public String getUnitMeasurement() {
    return unitMeasurement;
  }

  public void setUnitMeasurement(String unitMeasurement) {
    this.unitMeasurement = unitMeasurement;
  }

  public void setCasePack(String casePack) {
    this.casePack = casePack;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }
}
