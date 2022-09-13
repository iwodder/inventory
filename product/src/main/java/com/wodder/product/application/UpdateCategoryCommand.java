package com.wodder.product.application;

public class UpdateCategoryCommand {

  private String category;

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;
  }

  @Override
  public String toString() {
    return "UpdateCategoryCommand{category='" + category + '\'' + '}';
  }
}
