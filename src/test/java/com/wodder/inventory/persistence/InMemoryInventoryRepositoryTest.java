package com.wodder.inventory.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.domain.model.inventory.Inventory;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryInventoryRepositoryTest {

  private InMemoryInventoryRepository repo;

  @BeforeEach
  void setup() {
    repo = new InMemoryInventoryRepository();
  }

  @Test
  @DisplayName("Can save inventory to store")
  void save() {
    Inventory i = new Inventory();
    assertNotNull(repo.createItem(i));
  }

  @Test
  @DisplayName("Can load inventory from store")
  void load() {
    Inventory i = new Inventory();
    repo.createItem(i);
    Inventory i2 = repo.loadByItem(i).get();
    assertNotSame(i, i2);
    assertNotNull(i2);
  }

  @Test
  @DisplayName("Loading non-existent inventory returns empty optional")
  void load_non_existent() {
    Inventory i = new Inventory(LocalDate.of(2020, 1, 1));
    Optional<Inventory> result = repo.loadByItem(i);
    assertTrue(result.isEmpty());
  }
}
