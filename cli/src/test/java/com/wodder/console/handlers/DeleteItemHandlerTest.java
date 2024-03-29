package com.wodder.console.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wodder.console.ConsoleMenu;
import com.wodder.console.menus.inventoryitems.BaseMenuTest;
import com.wodder.inventory.dto.Result;
import com.wodder.product.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteItemHandlerTest extends BaseMenuTest {

  @Captor private ArgumentCaptor<ProductDto> captor;

  @Mock private com.wodder.console.models.InventoryItemModel model;

  @Mock private ConsoleMenu menu;

  @InjectMocks private DeleteItemHandler deleteItemHandler;

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "3"})
  @DisplayName("Can delete an existing item.")
  void handleInput(String id) {
    when(model.deleteItem(any(ProductDto.class))).thenReturn(new Result<>(Boolean.TRUE, null));
    setChars("id=" + id);
    deleteItemHandler.handleInput(in, out, err);

    verify(model).deleteItem(captor.capture());
    ProductDto dto = captor.getValue();
    assertEquals(id, dto.getId());
  }

  @ParameterizedTest
  @ValueSource(longs = {1L, 2L, 3L})
  @DisplayName("Prints success message when item is deleted successfully")
  void printsSuccessOnDelete(Long id) {
    when(model.deleteItem(any(ProductDto.class))).thenReturn(new Result<>(Boolean.TRUE, null));
    setChars("id=" + id);
    deleteItemHandler.handleInput(in, out, err);
    assertSuccessfulDelete(id);
  }

  @ParameterizedTest
  @ValueSource(longs = {1L, 2L, 3L})
  @DisplayName("Prints error message when delete fails")
  void printErrorOnDelete(Long id) {
    when(model.deleteItem(any(ProductDto.class)))
        .thenReturn(new Result<>(null, "Unable to delete item"));
    setChars("id=" + id);
    deleteItemHandler.handleInput(in, out, err);
    assertEquals(String.format("Unable to delete item with id of %d.%n", id), baosErr.toString());
  }

  @Test
  @DisplayName("When user enters exit menu exits")
  void exits() {
    setChars("exit");
    deleteItemHandler.handleInput(in, out, err);
    verify(menu).exitMenu();
  }

  @Test
  @DisplayName("User can enter just id to delete item")
  void enterNumberOnly() {
    when(model.deleteItem(any(ProductDto.class))).thenReturn(new Result<>(Boolean.TRUE, null));
    setChars("1");
    deleteItemHandler.handleInput(in, out, err);
    assertSuccessfulDelete(1L);
  }

  private void assertSuccessfulDelete(Long id) {
    assertEquals(
        String.format("Successfully deleted item with id of %d.%n", id), baosOut.toString());
  }
}
