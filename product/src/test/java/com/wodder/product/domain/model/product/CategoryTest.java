package com.wodder.product.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.product.domain.model.category.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

  @Test
  @DisplayName("Category defaults to unassigned")
  void name() {
    Category c = new Category();
    assertEquals("UNASSIGNED", c.getName());
  }

  @Test
  @DisplayName("Category can be assigned name during construction")
  void assign_name() {
    Category c = new Category("pantry");
    assertEquals("PANTRY", c.getName());
  }

  @Test
  @DisplayName("Category must be created with non-empty string")
  void not_blank() {
    Category c = new Category("");
    assertEquals("UNASSIGNED", c.getName());
  }

  @Test
  @DisplayName("Category gets created with upper-case name")
  void upperCaseName() {
    Category c = Category.of("frozen");
    assertEquals("FROZEN", c.getName());
  }

  @Test
  @DisplayName("Categories are equal based on name")
  void equal() {
    Category c1 = Category.of("froZen");
    Category c2 = Category.of("FRoZen");
    assertEquals(c1, c2);
  }
}
