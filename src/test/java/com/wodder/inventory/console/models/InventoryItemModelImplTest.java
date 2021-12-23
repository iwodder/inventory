package com.wodder.inventory.console.models;

import com.wodder.inventory.application.*;
import com.wodder.inventory.models.InventoryItemModel;
import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryItemModelImplTest {

	@Mock
	ItemStorage itemStorage;

	@InjectMocks
	InventoryItemModelImpl inventoryItemModel;

	@Test
	@DisplayName("Create item returns ok on success")
	void createItemSuccess() {
		when(itemStorage.addItem(any(InventoryItemModel.class))).thenReturn(Optional.of(InventoryItemModel.builder().build()));
		Result<InventoryItemModel, String> r = inventoryItemModel.createItem(InventoryItemModel.builder().build());
		assertTrue(r.isOK());
		assertFalse(r.isErr());
	}

	@Test
	@DisplayName("Create item returns error result on failure")
	void createItemFailure() {
		when(itemStorage.addItem(any(InventoryItemModel.class))).thenReturn(Optional.empty());
		Result<InventoryItemModel, String> r = inventoryItemModel.createItem(InventoryItemModel.builder().build());
		assertFalse(r.isOK());
		assertTrue(r.isErr());
	}

	@Test
	@DisplayName("Delete item returns ok on success")
	void deleteItemSuccess() {
		when(itemStorage.deleteItem(any(InventoryItemModel.class))).thenReturn(Boolean.TRUE);
		Result<Boolean, String> result = inventoryItemModel.deleteItem(InventoryItemModel.builder().build());
		assertTrue(result.isOK());
		assertFalse(result.isErr());
	}

	@Test
	@DisplayName("Delete item returns err on failure")
	void deleteItemFailure() {
		Result<Boolean, String> result = inventoryItemModel.deleteItem(InventoryItemModel.builder().build());
		assertTrue(result.isErr());
		assertFalse(result.isOK());
	}

	@Test
	@DisplayName("Update item returns ok on success")
	void updateItemSuccess() {
		when(itemStorage.updateItemCategory(any(InventoryItemModel.class))).thenReturn(Optional.of(InventoryItemModel.builder().build()));
		Result<InventoryItemModel, String> result = inventoryItemModel.updateItem(InventoryItemModel.builder().build());
		assertTrue(result.isOK());
		assertFalse(result.isErr());
	}

	@Test
	@DisplayName("Update item returns err on failure")
	void updateItemFailure() {
		Result<InventoryItemModel, String> result = inventoryItemModel.updateItem(InventoryItemModel.builder().build());
		assertTrue(result.isErr());
		assertFalse(result.isOK());
	}

	@Test
	@DisplayName("Get items returns err on failure")
	void getItemsFailure() {
		Result<List<InventoryItemModel>, String> result = inventoryItemModel.getItems();
		assertFalse(result.isOK());
		assertTrue(result.isErr());
	}

	@Test
	@DisplayName("Get items returns err on empty items list")
	void getItemsFailure1() {
		when(itemStorage.readAllItems()).thenReturn(Collections.emptyList());
		Result<List<InventoryItemModel>, String> result = inventoryItemModel.getItems();
		assertFalse(result.isOK());
		assertTrue(result.isErr());
	}

	@Test
	@DisplayName("Get items returns ok on success")
	void getItemsSuccess() {
		List<InventoryItemModel> items = new ArrayList<>();
		items.add(InventoryItemModel.builder().build());
		when(itemStorage.readAllItems()).thenReturn(items);
		Result<List<InventoryItemModel>, String> result = inventoryItemModel.getItems();
		assertFalse(result.isErr());
		assertTrue(result.isOK());
	}

}
