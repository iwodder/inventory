package com.wodder.inventory.application.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.application.InventoryService;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.Location;
import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.Product;
import com.wodder.inventory.domain.model.product.ProductId;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import com.wodder.inventory.dto.InventoryCountModel;
import com.wodder.inventory.dto.InventoryDto;
import com.wodder.inventory.persistence.PersistenceFactory;
import com.wodder.inventory.persistence.Repository;
import com.wodder.inventory.persistence.TestPersistenceFactory;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

  InventoryService invSvc;
  PersistenceFactory psf;

  @BeforeEach
  void setup() {
    psf = TestPersistenceFactory.getUnpopulated();
    invSvc =
        new InventoryServiceImpl(
            psf.getRepository(Inventory.class), psf.getRepository(Product.class));
  }

  @Test
  @DisplayName("Can create a new inventory")
  void createInventory() {
    InventoryDto mdl = invSvc.createInventory();
    assertNotNull(mdl);
    assertEquals(LocalDate.now(), mdl.getInventoryDate());
  }

  @Test
  @DisplayName("Creating a new inventory saves it to the database")
  void createInventory1() {
    InventoryDto model = invSvc.createInventory();
    InventoryDto result = invSvc.loadInventory(model.getId()).get();
    assertEquals(model, result);
  }

  @Test
  @DisplayName("Inventory is created with active products already added")
  void createInventory2() {
    initializeProducts();
    InventoryDto dto = invSvc.createInventory();
    assertCorrectItemsInInventory(3, dto);
  }

  @Test
  @DisplayName("Can add a single count to inventory")
  void addInventoryCount() {
    InventoryDto dto = invSvc.createInventory();
    initializeProducts();

    InventoryDto model = invSvc.addInventoryCount(dto.getId(), "234", 2.0, 0.25).get();

    assertCorrectItemsInInventory(1, model);
  }

  @Test
  @DisplayName("Cannot add inventory count to non-existant inventory")
  void addInventoryCountUnknown() {
    assertResultIsEmpty(invSvc.addInventoryCount("123", "abc", 2.0, 0.25));
  }

  @Test
  @DisplayName("Can add multiple inventory counts to inventory")
  void addInventoryCounts() {
    initializeProducts();
    InventoryDto inv = invSvc.createInventory();

    InventoryDto m =
        invSvc
            .addInventoryCounts(
                inv.getId(),
                Arrays.asList(
                    new InventoryCountModel("234", 1.0, 1.0),
                    new InventoryCountModel("345", .23, 0.25),
                    new InventoryCountModel("456", 1.1, 1.23)))
            .get();

    assertCorrectItemsInInventory(3, m);
  }

  private void assertResultIsEmpty(Optional<?> result) {
    assertTrue(result.isEmpty());
  }

  private void assertCorrectItemsInInventory(int numberOfItems, InventoryDto m) {
    assertEquals(numberOfItems, m.numberOfItems());
  }

  private void initializeProducts() {
    Repository<Product, ProductId> product = psf.getRepository(Product.class);
    product.createItem(
        new Product(
            ProductId.productIdOf("234"),
            "2% Milk",
            new Category("Dairy"),
            new Location("Refrigerator"),
            new UnitOfMeasurement("GAL", 4),
            new Price("2.35", "11.00")));
    product.createItem(
        new Product(
            ProductId.productIdOf("345"),
            "Cheese",
            new Category("Dairy"),
            new Location("Refrigerator"),
            new UnitOfMeasurement("GAL", 4),
            new Price("2.35", "11.00")));
    product.createItem(
        new Product(
            ProductId.productIdOf("456"),
            "Yogurt",
            new Category("Dairy"),
            new Location("Refrigerator"),
            new UnitOfMeasurement("GAL", 4),
            new Price("2.35", "11.00")));
  }
}
