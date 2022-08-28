package com.wodder.product.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.product.domain.model.PersistenceFactory;
import com.wodder.product.domain.model.category.Category;
import com.wodder.product.dto.CategoryModel;
import com.wodder.product.persistence.PersistenceFactoryImpl;
import com.wodder.product.persistence.TestPersistenceFactory;
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
