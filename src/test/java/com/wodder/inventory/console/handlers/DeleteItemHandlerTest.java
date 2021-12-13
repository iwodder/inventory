package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteItemHandlerTest extends BaseMenuTest {

	@Captor
	private ArgumentCaptor<InventoryItemModel> captor;

	@Mock
	private com.wodder.inventory.console.models.InventoryItemModel model;

	@Mock
	private ConsoleMenu menu;

	@InjectMocks
	private DeleteItemHandler deleteItemHandler;

	@ParameterizedTest
	@ValueSource(longs = { 1L, 2L, 3L })
	@DisplayName("Can delete an existing item.")
	void handleInput(Long id) {
		when(model.deleteItem(any(InventoryItemModel.class))).thenReturn(new Result<>(Boolean.TRUE, null));
		setChars("id=" + id);
		deleteItemHandler.handleInput(in, out, err);

		verify(model).deleteItem(captor.capture());
		InventoryItemModel dto = captor.getValue();
		assertEquals(id, dto.getId());
	}

	@ParameterizedTest
	@ValueSource(longs = { 1L, 2L, 3L })
	@DisplayName("Prints success message when item is deleted successfully")
	void printsSuccessOnDelete(Long id) {
		when(model.deleteItem(any(InventoryItemModel.class))).thenReturn(new Result<>(Boolean.TRUE, null));
		setChars("id=" + id);
		deleteItemHandler.handleInput(in, out, err);
		assertSuccessfulDelete(id);
	}

	@ParameterizedTest
	@ValueSource(longs = { 1L, 2L, 3L })
	@DisplayName("Prints error message when delete fails")
	void printErrorOnDelete(Long id) {
		when(model.deleteItem(any(InventoryItemModel.class))).thenReturn(new Result<>(null, "Unable to delete item"));
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
		when(model.deleteItem(any(InventoryItemModel.class))).thenReturn(new Result<>(Boolean.TRUE, null));
		setChars("1");
		deleteItemHandler.handleInput(in, out, err);
		assertSuccessfulDelete(1L);
	}

	private void assertSuccessfulDelete(Long id) {
		assertEquals(String.format("Successfully deleted item with id of %d.%n", id), baosOut.toString());
	}
}
