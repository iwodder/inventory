package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventory.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;
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
	InventoryItemModel inventoryItemModel;

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
	@DisplayName("User is prompted with '>' for input")
	void handleInput() {
		setChars("");
		viewItemHandler.handleInput(in, out, err);
		assertTrue(baosOut.toString().startsWith("> "));
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
	void displaysItems() throws Exception {
		InputStream is = this.getClass().getResourceAsStream("expectedItemOutput");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = br.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		setChars("all");
		mockSuccessfulReturn();
		viewItemHandler.handleInput(in, out, err);
		assertEquals(sb.toString(), "");
	}

	private void mockSuccessfulReturn() {
		List<InventoryItemDto> items = new ArrayList<>();
		items.add(InventoryItemDto.builder().withId(1L).withName("bread").build());
		items.add(InventoryItemDto.builder().withId(2L).withName("milk").build());
		when(inventoryItemModel.getItems()).thenReturn(new Result<>(items, null));
	}
}
