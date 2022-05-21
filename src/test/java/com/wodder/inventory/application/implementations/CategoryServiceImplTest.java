package com.wodder.inventory.application.implementations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.application.CategoryService;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.dto.CategoryModel;
import com.wodder.inventory.persistence.PersistenceFactory;
import com.wodder.inventory.persistence.PersistenceFactoryImpl;
import com.wodder.inventory.persistence.TestPersistenceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryServiceImplTest {

  CategoryService svc;

  @BeforeEach
  public void setup() {
    PersistenceFactory psf = TestPersistenceFactory.getPopulated();
    svc = new CategoryServiceImpl(psf.getRepository(Category.class));
  }

  @Test
  @DisplayName("Is able to create a category when it doesn't exist")
  void createCategory() {
    assertTrue(svc.createCategory("Fantasy Food").isPresent());
  }

  @Test
  @DisplayName("If item exists then empty optional is returned")
  void create_category_fails() {
    svc = new CategoryServiceImpl(new PersistenceFactoryImpl().getRepository(Category.class));
    svc.createCategory("Dry Goods");
    assertTrue(svc.createCategory("Dry Goods").isEmpty());
  }

  @Test
  @DisplayName("Loads a category that exists")
  void loadCategory() {
    CategoryModel m = svc.createCategory("Fantasy Food").get();
    assertTrue(svc.loadCategory(m.getId()).isPresent());
  }
}
