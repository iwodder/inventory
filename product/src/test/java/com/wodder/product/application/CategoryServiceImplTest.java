package com.wodder.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.product.domain.model.PersistenceFactory;
import com.wodder.product.dto.CategoryDto;
import com.wodder.product.persistence.TestPersistenceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryServiceImplTest {

  CategoryService svc;

  @BeforeEach
  public void setup() {
    PersistenceFactory psf = TestPersistenceFactory.getPopulated();
    svc = new CategoryServiceImpl(psf.getCategoryRepository());
  }

  @Test
  @DisplayName("Is able to create a category when it doesn't exist")
  void createCategory() {
    CategoryDto dto = svc.createCategory("Fantasy Food");
    assertNotNull(dto);
    assertEquals("FANTASY FOOD", dto.getName());
  }

  @Test
  @DisplayName("If item exists then it is returned")
  void create_category_fails() {
    CategoryDto dto = svc.createCategory("Dry Goods");
    assertNotNull(svc.createCategory("Dry Goods"));
  }

  @Test
  @DisplayName("Loads a category that exists")
  void loadCategory() {
    CategoryDto m = svc.createCategory("Fantasy Food");
    assertTrue(svc.loadCategory(m.getId()).isPresent());
  }
}
