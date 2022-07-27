package com.wodder.console.menus.inventoryitems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.console.handlers.DeleteItemHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteItemMenuTest extends BaseMenuTest {

  @Mock
  DeleteItemHandler handler;

  @Test
  @DisplayName("Delete Item Menu prints menu")
  void delete_item_menu_print() {
    DeleteItemMenu deleteItemMenu = new DeleteItemMenu(handler);
    deleteItemMenu.printMenu(out);
    String expected =
        String.format(
            "====== Delete Item ======%nEnter item id or name to delete, e.g. 1 or \"bread\"%n");
    assertEquals(expected, baosOut.toString());
  }
}
