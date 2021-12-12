package com.wodder.inventory.models;

import org.junit.jupiter.api.*;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemModelTest {

	@Test
	@DisplayName("Creates InventoryItemModel from map")
	void createDtoFromMap() {
		Map<String, String> values = new HashMap<>();
		values.put("NAME", "bread");
		values.put("ID", "1");
		values.put("CATEGORY", "refrigerated");
		InventoryItemModel result = InventoryItemModel.fromMap(values);
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("bread", result.getName());
		assertEquals("refrigerated", result.getCategory());
	}

	@Test
	@DisplayName("Creates InventoryItemModel from map with missing id")
	void createDtoFromMapMissingId() {
		Map<String, String> values = new HashMap<>();
		values.put("NAME", "bread");
		values.put("CATEGORY", "refrigerated");
		InventoryItemModel result = InventoryItemModel.fromMap(values);
		assertNotNull(result);
		assertNull(result.getId());
		assertEquals("bread", result.getName());
		assertEquals("refrigerated", result.getCategory());
	}

	@Test
	@DisplayName("Null map returns an empty InventoryItemModel")
	void nullMap() throws Exception {
		InventoryItemModel result = InventoryItemModel.fromMap(null);
		assertNotNull(result);
		assertGettersReturnNull(result);
	}

	@Test
	@DisplayName("Empty map returns an empty InventoryItemModel")
	void emptyMap() throws Exception {
		InventoryItemModel result = InventoryItemModel.fromMap(new HashMap<>());
		assertNotNull(result);
		assertGettersReturnNull(result);
	}

	private void assertGettersReturnNull(InventoryItemModel result) throws IllegalAccessException, InvocationTargetException {
		Method[] methods = InventoryItemModel.class.getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("get")) {
				assertNull(m.invoke(result));
			}
		}
	}
}
