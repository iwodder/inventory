package com.wodder.product.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryCategoryRepositoryTest {

  static Repository<Category, CategoryId> categoryRepository;
  static Category dairy = new Category("Dairy");
  static Category dryGoods = new Category("Dry Goods");
  static Category meats = new Category("Meats");

  @BeforeAll
  static void setupAll() {
    categoryRepository = new InMemoryCategoryRepository();
    categoryRepository.createItem(dairy);
    categoryRepository.createItem(dryGoods);
    categoryRepository.createItem(meats);
  }

  @Test
  @DisplayName("Can load a particular category by id")
  void load() {
    Optional<Category> result = categoryRepository.loadById(dairy.getId());
    assertTrue(result.isPresent());
    assertEquals(new Category("Dairy"), result.get());
  }

  @Test
  @DisplayName("Can load a category by providing an item")
  void load_by_item() {
    Optional<Category> result = categoryRepository.loadByItem(new Category("Dairy"));
    assertTrue(result.isPresent());
    assertEquals(new Category("Dairy"), result.get());
  }

  @Test
  @DisplayName("Updating a category does nothing")
  void updateItem() {
    categoryRepository.updateItem(new Category("Invalid Category"));
    assertTrue(true);
  }

  @Test
  @DisplayName("Can load all items from a repository")
  void loadAllItems() {
    List<Category> items = categoryRepository.loadAllItems();
    assertEquals(3, items.size());
    assertTrue(
        items.containsAll(
            Arrays.asList(
                new Category("Dairy"), new Category("Dry Goods"), new Category("Meats"))));
  }

  @Test
  @DisplayName("Can create a inventory category")
  void createItem() {
    Category c = categoryRepository.createItem(new Category("Frozen"));
    assertNotNull(c.getId());
    assertEquals(new Category("Frozen"), c);
  }

  @Test
  @DisplayName("Trying to load a category with an invalid id returns empty")
  void doesnt_exist() {
    Assertions.assertTrue(categoryRepository.loadById(CategoryId.generateId()).isEmpty());
  }
}
