package com.wodder.inventory.console.models;

import com.wodder.inventory.domain.*;
import com.wodder.inventory.dtos.*;
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

	@BeforeEach
	void setup() {
	}

	@Test
	@DisplayName("Create item returns ok on success")
	void createItem() {
		when(itemStorage.addItem(any(InventoryItemDto.class))).thenReturn(Optional.of(InventoryItemDto.builder().build()));
		InventoryItemModelImpl inventoryItemModel = new InventoryItemModelImpl(itemStorage);
		Result<InventoryItemDto, String> r = inventoryItemModel.createItem(InventoryItemDto.builder().build());
		assertTrue(r.isOK());
		assertFalse(r.isErr());
	}

	@Test
	@DisplayName("Create item returns error result on failure")
	void createItem1() {
		when(itemStorage.addItem(any(InventoryItemDto.class))).thenReturn(Optional.empty());
		InventoryItemModelImpl inventoryItemModel = new InventoryItemModelImpl(itemStorage);
		Result<InventoryItemDto, String> r = inventoryItemModel.createItem(InventoryItemDto.builder().build());
		assertFalse(r.isOK());
		assertTrue(r.isErr());
	}
}
