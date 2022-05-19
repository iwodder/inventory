package com.wodder.inventory.console.models;

import com.wodder.inventory.application.*;
import com.wodder.inventory.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductModelImplTest {

	@Mock
	ProductService productService;

	@InjectMocks
	InventoryItemModelImpl inventoryItemModel;

	@Test
	@DisplayName("Create item returns ok on success")
	void createItemSuccess() {
		when(productService.createNewProduct(any(ProductModel.class))).thenReturn(Optional.of(ProductModel.builder().build()));
		Result<ProductModel, String> r = inventoryItemModel.createItem(ProductModel.builder().build());
		assertTrue(r.isOK());
		assertFalse(r.isErr());
	}

	@Test
	@DisplayName("Create item returns error result on failure")
	void createItemFailure() {
		when(productService.createNewProduct(any(ProductModel.class))).thenReturn(Optional.empty());
		Result<ProductModel, String> r = inventoryItemModel.createItem(ProductModel.builder().build());
		assertFalse(r.isOK());
		assertTrue(r.isErr());
	}

	@Test
	@Disabled
	@DisplayName("Delete item returns ok on success")
	void deleteItemSuccess() {
		Result<Boolean, String> result = inventoryItemModel.deleteItem(ProductModel.builder().build());
		assertTrue(result.isOK());
		assertFalse(result.isErr());
	}

	@Test
	@DisplayName("Delete item returns err on failure")
	void deleteItemFailure() {
		Result<Boolean, String> result = inventoryItemModel.deleteItem(ProductModel.builder().build());
		assertTrue(result.isErr());
		assertFalse(result.isOK());
	}

	@Test
	@DisplayName("Update item returns ok on success")
	void updateItemSuccess() {
		when(productService.updateProductCategory(any(), any())).thenReturn(Optional.of(ProductModel.builder().build()));
		Result<ProductModel, String> result = inventoryItemModel.updateItem(ProductModel.builder().build());
		assertTrue(result.isOK());
		assertFalse(result.isErr());
	}

	@Test
	@DisplayName("Update item returns err on failure")
	void updateItemFailure() {
		Result<ProductModel, String> result = inventoryItemModel.updateItem(ProductModel.builder().build());
		assertTrue(result.isErr());
		assertFalse(result.isOK());
	}

	@Test
	@DisplayName("Get items returns err on failure")
	void getItemsFailure() {
		Result<List<ProductModel>, String> result = inventoryItemModel.getItems();
		assertFalse(result.isOK());
		assertTrue(result.isErr());
	}

	@Test
	@DisplayName("Get items returns err on empty items list")
	void getItemsFailure1() {
		when(productService.loadAllActiveProducts()).thenReturn(Collections.emptyList());
		Result<List<ProductModel>, String> result = inventoryItemModel.getItems();
		assertFalse(result.isOK());
		assertTrue(result.isErr());
	}

	@Test
	@DisplayName("Get items returns ok on success")
	void getItemsSuccess() {
		List<ProductModel> items = new ArrayList<>();
		items.add(ProductModel.builder().build());
		when(productService.loadAllActiveProducts()).thenReturn(items);
		Result<List<ProductModel>, String> result = inventoryItemModel.getItems();
		assertFalse(result.isErr());
		assertTrue(result.isOK());
	}

}
