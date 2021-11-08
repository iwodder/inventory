package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.handlers.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteItemMenuTest extends BaseMenuTest {

	@Mock
	DeleteItemHandler handler;

	@Test
	@DisplayName("Delete Item Menu prints menu")
	void delete_item_menu_print() {
		DeleteItemMenu deleteItemMenu = new DeleteItemMenu(handler);
		deleteItemMenu.printMenu(out);
		String expected = String.format("====== Delete Item ======%nEnter item id or name to delete, e.g. 1 or \"bread\"%n");
		assertEquals(expected, baosOut.toString());
	}
}
