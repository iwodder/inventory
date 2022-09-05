package com.wodder.product.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.wodder.product.domain.model.category.Category;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostgresCategoryRepositoryIT extends BasePostgresTest {

  PostgresCategoryRepository repo;

  @BeforeEach
  void setup() {
    repo = new PostgresCategoryRepository(ds);
  }

  @AfterEach
  void teardown() {
    try (Connection c = ds.getConnection();
        Statement s = c.createStatement();
    ) {
      boolean result = s.execute("DELETE FROM product.categories");
      assertFalse(result, "Didn't expect a result set");
    } catch (Exception e) {
      fail("Couldn't clean up after test method.");
    }
  }

  @Test
  @DisplayName("Should be able to create a category")
  void create() {
    Category c = new Category("Frozen");
    Category result = repo.createItem(c);

    assertNotNull(result);
    assertEquals(c, result);
    assertNotNull(result.getDatabaseId());
  }

  @Test
  @DisplayName("Should be able to retrieve a category")
  void load() {
    Category expected = new Category("Frozen");
    repo.createItem(expected);

    Optional<Category> result = repo.loadById(expected.getId());
    assertTrue(result.isPresent());
    assertEquals(result.get(), expected, "Loaded item wasn't equal.");
  }

  @Test
  @DisplayName("Should be able to load all categories")
  void loadAll() {
    Category c1 = new Category("Frozen");
    Category c2 = new Category("Dry Goods");
    repo.createItem(c1);
    repo.createItem(c2);

    List<Category> result = repo.loadAllItems();
    assertEquals(2, result.size(), "Expected two items.");
    assertTrue(result.contains(c1));
    assertTrue(result.contains(c2));
  }

  @Test
  @DisplayName("Should be able to delete a category by id")
  void delete() {
    Category c2 = new Category("Dry Goods");
    repo.createItem(c2);

    repo.deleteItem(c2.getId());

    assertTrue(repo.loadById(c2.getId()).isEmpty());
  }

  @Test
  @DisplayName("Should be able to delete a category by object")
  void delete_by_object() {
    Category c2 = new Category("Dry Goods");
    repo.createItem(c2);

    repo.deleteItem(c2);

    assertTrue(repo.loadById(c2.getId()).isEmpty());
  }

  @Test
  @DisplayName("Should be able to load a category by object")
  void load_by_item() {
    Category c = new Category("Frozen");
    repo.createItem(c);

    Optional<Category> opt = repo.loadByItem(c);
    assertTrue(opt.isPresent());
    assertEquals(c, opt.get(), "Retrieved item should match");
  }

  @Test
  @DisplayName("Should be able to load a category by name")
  void load_by_name() {
    Category c = new Category("Frozen");
    repo.createItem(c);

    Optional<Category> opt = repo.loadByName(c.getName());
    assertTrue(opt.isPresent());
    assertEquals(c, opt.get(), "Retrieved item should match");
  }
}
