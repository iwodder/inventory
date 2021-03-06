package com.wodder.inventory.domain.model.product;

import com.wodder.inventory.domain.model.Entity;
import com.wodder.inventory.dto.CategoryModel;
import java.util.Objects;

public class Category extends Entity<CategoryId> {
  private static final String DEFAULT_NAME = "UNASSIGNED";
  private String name;

  public Category() {
    this(DEFAULT_NAME);
  }

  public Category(String name) {
    this(new CategoryId(), name);
  }

  public Category(Category that) {
    this(that.getId(), that.getName());
  }

  public Category(CategoryId id, String name) {
    super(id);
    setName(name);
  }

  public static Category defaultCategory() {
    return new Category();
  }

  public static Category of(String name) {
    return new Category(name);
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    if (name != null && name.length() > 0) {
      this.name = name.toUpperCase();
    } else {
      this.name = DEFAULT_NAME;
    }
  }

  public CategoryModel toModel() {
    return new CategoryModel(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return getName().equals(category.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  @Override
  public String toString() {
    return name;
  }
}
