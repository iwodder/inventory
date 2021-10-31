package com.wodder.inventory.console.models;

import com.wodder.inventory.domain.*;
import com.wodder.inventory.dtos.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemModelImplTest{

	@Test
	@DisplayName("Create item returns ok on success")
	void createItem() {
		TestServiceFactory testServiceFactory = new TestServiceFactory();
		InventoryItemModelImpl inventoryItemModel = new InventoryItemModelImpl(testServiceFactory.getService(ItemStorage.class));
		Result<InventoryItemDto, String> r = inventoryItemModel.createItem(InventoryItemDto.builder().build());
		assertTrue(r.isOK());
		assertFalse(r.isErr());
	}

	@Test
	@DisplayName("Create item returns error result on failure")
	void createItem1() {
		TestServiceFactory testServiceFactory = new TestServiceFactory(false);
		InventoryItemModelImpl inventoryItemModel = new InventoryItemModelImpl(testServiceFactory.getService(ItemStorage.class));
		Result<InventoryItemDto, String> r = inventoryItemModel.createItem(InventoryItemDto.builder().build());
		assertFalse(r.isOK());
		assertTrue(r.isErr());
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
