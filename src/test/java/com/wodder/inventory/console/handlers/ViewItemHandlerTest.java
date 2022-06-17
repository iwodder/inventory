package com.wodder.inventory.console.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wodder.inventory.console.ConsoleMenu;
import com.wodder.inventory.console.menus.inventoryitems.BaseMenuTest;
import com.wodder.inventory.dto.ProductDto;
import com.wodder.inventory.dto.Result;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ViewItemHandlerTest extends BaseMenuTest {

  @Mock com.wodder.inventory.console.models.InventoryItemModel inventoryItemModel;

  @Mock ConsoleMenu menu;

  private ViewItemHandler viewItemHandler;

  @BeforeEach
  protected void setup() {
    super.setup();
    viewItemHandler = new ViewItemHandler(inventoryItemModel);
    viewItemHandler.setMenu(menu);
  }

  @Test
  @DisplayName("All items are returned when \"all\" is input.")
  void handleAll() {
    setChars("all");
    mockSuccessfulReturn();
    viewItemHandler.handleInput(in, out, err);
    verify(inventoryItemModel).getItems();
  }

  @Test
  @DisplayName("Exit menu when user enters \"exit\"")
  void exitMenu() {
    setChars("exit");
    viewItemHandler.handleInput(in, out, err);
    verify(menu).exitMenu();
  }

  @Test
  @DisplayName("Error message is printed on err")
  void printsErrMessage() {
    setChars("all");
    when(inventoryItemModel.getItems()).thenReturn(new Result<>(null, "Err"));
    viewItemHandler.handleInput(in, out, err);
    assertEquals(String.format("Err%n"), baosErr.toString());
  }

  @Test
  @DisplayName("Displays items when returned")
  void displaysItems() {
    setChars("all");
    mockSuccessfulReturn();
    viewItemHandler.handleInput(in, out, err);
    assertEquals(expectedDisplayText(), baosOut.toString());
  }

  private String expectedDisplayText() {
    StringBuilder sb = new StringBuilder();
    try {
      InputStream is = this.getClass().getResourceAsStream("expectedItemOutput");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String s;
      while ((s = br.readLine()) != null) {
        sb.append(s);
        sb.append(System.lineSeparator());
      }
    } catch (IOException e) {
      fail(
          String.format("IOException occurred when processing expected input. %s", e.getMessage()));
    }
    return sb.toString();
  }

  private void mockSuccessfulReturn() {
    List<ProductDto> items = new ArrayList<>();
    items.add(ProductDto.builder().withId("1").withName("bread").withCategory("dry").build());
    items.add(
        ProductDto.builder().withId("2").withName("milk").withCategory("refrigerated").build());
    when(inventoryItemModel.getItems()).thenReturn(new Result<>(items, null));
  }
}
