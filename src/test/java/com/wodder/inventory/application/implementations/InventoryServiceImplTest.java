package com.wodder.inventory.application.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.wodder.inventory.application.inventory.InventoryService;
import com.wodder.inventory.application.inventory.InventoryServiceImpl;
import com.wodder.inventory.domain.model.inventory.Count;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.ItemId;
import com.wodder.inventory.dto.CountDto;
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
    invSvc = new InventoryServiceImpl(psf.getInventoryRepository(), psf.getRepository(Item.class));
  }

  @Test
  @DisplayName("Creating a new inventory saves it to the database")
  void createInv() {
    Inventory model = invSvc.createInventory();

    Inventory result =
        invSvc.loadInventory(model.getId().getValue())
            .orElseGet(() ->
                fail(String.format("Expected to find inventory of id [ %s ]", model.getId())));

    assertEquals(model, result);
    assertInventoryDateIsToday(model);
  }

  @Test
  @DisplayName("Inventory is created with active products")
  void populatedInv() {
    initializeItems();
    Inventory dto = invSvc.createInventory();

    assertCorrectItemsInInventory(3, dto);
  }

  @Test
  @DisplayName("Inventory is created without any products")
  void emptyInv() {
    Inventory dto = invSvc.createEmptyInventory();

    assertCorrectItemsInInventory(0, dto);
  }

  @Test
  @DisplayName("Can change a single count to inventory")
  void addInventoryCount() {
    initializeItems();
    Inventory dto = invSvc.createInventory();

    Inventory result = invSvc.changeInventoryCount(
        dto.getId().getValue(),
        "item:123",
        "2.0",
        "0.25").get();


    assertEquals(Count.countOf("2.0", "0.25"), result.getCount("2% Milk").get());
  }

  @Test
  @DisplayName("Cannot add inventory count to non-existent inventory")
  void addInventoryCountUnknown() {
    assertResultIsEmpty(invSvc.changeInventoryCount("123", "abc", "2.0", "0.25"));
  }

  @Test
  @DisplayName("Can add multiple inventory counts to inventory")
  void addInventoryCounts() {
    initializeItems();
    String id = invSvc.createInventory().getId().getValue();

    Inventory m =
        invSvc.updateInventoryCounts(
                id,
                Arrays.asList(
                    new CountDto("item:123", 1.0, 1.0),
                    new CountDto("item:234", .23, 0.25),
                    new CountDto("item:345", 1.1, 1.23)))
            .get();

    assertEquals(
        Count.countOf("1.0", "1.0"), invSvc.getCount(id, "item:123").get());
    assertEquals(
        Count.countOf(".23", ".25"), invSvc.getCount(id, "item:234").get());
    assertEquals(
        Count.countOf("1.1", "1.23"), invSvc.getCount(id, "item:345").get());
  }

  @Test
  @DisplayName("Should be able to load a saved inventory")
  void loadInventory() {
    Inventory dto = invSvc.createInventory();

    assertResultIsPresent(invSvc.loadInventory(dto.getId().getValue()));
  }

  @Test
  @DisplayName("Should not find non-existent inventory id")
  void loadInventory2() {
    assertResultIsEmpty(invSvc.loadInventory("abc-123"));
  }

  private void assertResultIsEmpty(Optional<?> result) {
    assertTrue(result.isEmpty());
  }

  private void assertResultIsPresent(Optional<?> result) {
    assertTrue(result.isPresent());
  }

  private void assertCorrectItemsInInventory(int numberOfItems, Inventory m) {
    assertEquals(numberOfItems, m.numberOfItems());
  }

  private void assertInventoryDateIsToday(Inventory mdl) {
    assertEquals(LocalDate.now(), mdl.getInventoryDate());
  }

  private void initializeItems() {
    Repository<Item, ItemId> itemRepository = psf.getRepository(Item.class);
    itemRepository.createItem(
        Item.builder()
            .withId("item:123")
            .withName("2% Milk")
            .withLocation("Refrigerator")
            .withUnits("Gallon")
            .build()
    );
    itemRepository.createItem(
        Item.builder()
            .withId("item:234")
            .withName("Fabric Softener")
            .withLocation("Laundry Room")
            .withUnits("Gallon")
            .build()
    );
    itemRepository.createItem(
        Item.builder()
            .withId("item:345")
            .withName("Chicken Breast")
            .withLocation("Refrigerator")
            .withUnits("Pounds")
            .build()
    );
  }
}
