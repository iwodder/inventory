package com.wodder.inventory.console.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wodder.inventory.console.ConsoleMenu;
import com.wodder.inventory.console.menus.inventoryitems.BaseMenuTest;
import com.wodder.inventory.dto.ProductModel;
import com.wodder.inventory.dto.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateItemHandlerTest extends BaseMenuTest {

  @Captor ArgumentCaptor<ProductModel> captor;

  @Mock com.wodder.inventory.console.models.InventoryItemModel model;

  @Mock ConsoleMenu menu;

  @Test
  @DisplayName("Can update an item with only id and name")
  void updateItem() {
    when(model.updateItem(any())).thenReturn(new Result<>(ProductModel.builder().build(), null));
    setChars("id=1 name=milk");
    UpdateItemHandler handler = new UpdateItemHandler(model);
    handler.handleInput(in, out, err);
    verify(model).updateItem(captor.capture());

    ProductModel dto = captor.getValue();
    assertNotNull(dto);
    assertEquals("1", dto.getId());
    assertEquals("milk", dto.getName());
    assertEquals(String.format("Item updated successfully%n"), baosOut.toString());
  }

  @Test
  @DisplayName("Can update an item with only id and category")
  void updateItem1() {
    when(model.updateItem(any())).thenReturn(new Result<>(ProductModel.builder().build(), null));
    setChars("id=1 category=refrigerated");
    UpdateItemHandler handler = new UpdateItemHandler(model);
    handler.handleInput(in, out, err);
    verify(model).updateItem(captor.capture());

    ProductModel dto = captor.getValue();
    assertNotNull(dto);
    assertEquals("1", dto.getId());
    assertEquals("refrigerated", dto.getCategory());
    assertEquals(String.format("Item updated successfully%n"), baosOut.toString());
  }

  @Test
  @DisplayName("Displays a failure message when item cannot be updated")
  void updateItemFailure() {
    when(model.updateItem(any())).thenReturn(new Result<>(null, "Exception during creation"));
    setChars("id=1 name=milk");
    UpdateItemHandler handler = new UpdateItemHandler(model);
    handler.handleInput(in, out, err);
    String expected = String.format("Unable to update item%nException during creation%n");
    assertEquals(expected, baosErr.toString());
  }

  @Test
  @DisplayName("When user enters exit menu is exited")
  void exitMenu() {
    setChars("exit");
    UpdateItemHandler handler = new UpdateItemHandler(model);
    handler.setMenu(menu);
    handler.handleInput(in, out, err);
    verify(menu).exitMenu();
  }
}
