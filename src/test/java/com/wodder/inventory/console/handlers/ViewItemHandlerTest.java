package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewItemHandlerTest extends BaseMenuTest {

	@Mock
	com.wodder.inventory.console.models.InventoryItemModel inventoryItemModel;

	@Mock
	ConsoleMenu menu;

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
			fail(String.format("IOException occurred when processing expected input. %s", e.getMessage()));
		}
		return sb.toString();
	}

	private void mockSuccessfulReturn() {
		List<ProductModel> items = new ArrayList<>();
		items.add(ProductModel.builder().withId(1L).withName("bread").withCategory("dry").build());
		items.add(ProductModel.builder().withId(2L).withName("milk").withCategory("refrigerated").build());
		when(inventoryItemModel.getItems()).thenReturn(new Result<>(items, null));
	}
}
