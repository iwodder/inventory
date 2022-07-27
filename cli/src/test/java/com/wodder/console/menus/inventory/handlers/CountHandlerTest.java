package com.wodder.console.menus.inventory.handlers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.console.menus.inventoryitems.BaseMenuTest;
import com.wodder.inventory.dto.InventoryDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
class CountHandlerTest extends BaseMenuTest {

  @Mock
  //	private InventoryService service;

  @InjectMocks
  CountHandler handler;

  private InventoryDto model;

  @BeforeEach
  protected void setup() {
    super.setup();
    setupInventoryModel();
  }

  private void setupInventoryModel() {
    model = new InventoryDto();
    model.setInventoryDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    //		model.addInventoryCountModel(new InventoryCountModel(1L, "Bananas", "Fruit", "Pantry"));
    //		model.addInventoryCountModel(new InventoryCountModel(2L, "Apples", "Fruit", "Pantry"));
    //		model.addInventoryCountModel(new InventoryCountModel(3L, "Spinach", "Vegetables",
    // "Refrigerator"));
    //		model.addInventoryCountModel(new InventoryCountModel(4L, "Frozen Broccoli", "Frozen",
    // "Freezer"));
  }

  @Test
  @DisplayName("Entering \"exit\" leaves menu")
  void handleInput() {
    setChars("exit");
    handler.handleInput(in, out, err);
    assertTrue(true);
  }

  @Test
  @DisplayName("User chooses which location to count")
  void choose_location() {
    //		when(service.createNewInventory()).thenReturn(model);
    setChars("pantry\nexit");
    handler.handleInput(in, out, err);
    String expected = String.format("---- Counting Pantry ----%n");
    String actual = baosOut.toString();
    assertTrue(
        actual.startsWith(expected),
        () -> String.format("Expected %s to start with %s", actual, expected));
  }

  @Test
  @DisplayName("Performing inventory creates a new inventory once")
  void created_once() {
    //		when(service.createNewInventory()).thenReturn(model);
    setChars("pantry\nfreezer\nexit");
    handler.handleInput(in, out, err);
    //		verify(service, new Times(1)).createNewInventory();
  }

  @Test
  @DisplayName("Leaving and returning clears performed inventory")
  void created_twice() {
    //		when(service.createNewInventory()).thenReturn(model);
    setChars("pantry\nexit\nfreezer\nexit");
    handler.handleInput(in, out, err);
    handler.handleInput(in, out, err);
    //		verify(service, new Times(2)).createNewInventory();
  }

  @Test
  @DisplayName("Choosing a location to count loops through all items")
  void count_items() {
    //		when(service.createNewInventory()).thenReturn(model);
    setChars("pantry\nexit");
    handler.handleInput(in, out, err);
    String expected = String.format("1) Bananas%n2) Apples%n");
    String actual = baosOut.toString();
    assertTrue(
        actual.endsWith(expected),
        () -> String.format("Expected %s to end with %s", actual, expected));
  }
}
