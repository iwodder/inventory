package com.wodder.inventory.console.models;

import com.wodder.inventory.domain.*;
import com.wodder.inventory.dtos.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemModelImplTest implements Listener {
	boolean success;
	boolean error;

	@BeforeEach
	void setup() {
		success = false;
		error = false;
	}

	@Test
	@DisplayName("Create item notifies listeners on completion")
	void createItem() {
		InventoryItemModelImpl inventoryItemModel = new InventoryItemModelImpl(new TestServiceFactory());
		inventoryItemModel.registerListener(this);
		inventoryItemModel.createItem(InventoryItemDto.builder().build());
		assertTrue(success);
		assertFalse(error);
	}

	@Test
	@DisplayName("Create item notifies listener on error")
	void createItem1() {
		InventoryItemModelImpl inventoryItemModel = new InventoryItemModelImpl(new TestServiceFactory(false));
		inventoryItemModel.registerListener(this);
		inventoryItemModel.createItem(InventoryItemDto.builder().build());
		assertFalse(success);
		assertTrue(error);
	}

	public void onSuccess() {
		success = true;
	}

	public void onError() {
		error = true;
	}

	private static class TestServiceFactory implements ServiceFactory {
		boolean present;

		TestServiceFactory() {
			this(true);
		}

		TestServiceFactory(boolean onOff) {
			present = onOff;
		}

		@Override
		public <T> T getService(Class<T> service) {
			return (T) new ItemStorage() {

				@Override
				public Optional<InventoryItemDto> addItem(InventoryItemDto newItem) {
					if (present) {
						return Optional.of(newItem);
					} else {
						return Optional.empty();
					}
				}

				@Override
				public Boolean deleteItem(InventoryItemDto itemToDelete) {
					return null;
				}

				@Override
				public Optional<InventoryItemDto> updateItem(InventoryItemDto updatedItem) {
					return Optional.empty();
				}

				@Override
				public Optional<InventoryItemDto> readItem(Long itemId) {
					return Optional.empty();
				}

				@Override
				public List<InventoryItemDto> readAllItems() {
					return null;
				}
			};
		}
	}
}
