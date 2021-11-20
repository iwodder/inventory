package com.wodder.inventory.dtos;

import org.junit.jupiter.api.*;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemDtoTest {

	@Test
	@DisplayName("Creates InventoryItemDto from map")
	void createDtoFromMap() {
		Map<String, String> values = new HashMap<>();
		values.put("NAME", "bread");
		values.put("ID", "1");
		values.put("CATEGORY", "refrigerated");
		InventoryItemDto result = InventoryItemDto.fromMap(values);
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("bread", result.getName());
		assertEquals("refrigerated", result.getCategory());
	}

	@Test
	@DisplayName("Creates InventoryItemDto from map with missing id")
	void createDtoFromMapMissingId() {
		Map<String, String> values = new HashMap<>();
		values.put("NAME", "bread");
		values.put("CATEGORY", "refrigerated");
		InventoryItemDto result = InventoryItemDto.fromMap(values);
		assertNotNull(result);
		assertNull(result.getId());
		assertEquals("bread", result.getName());
		assertEquals("refrigerated", result.getCategory());
	}

	@Test
	@DisplayName("Null map returns an empty InventoryItemDto")
	void nullMap() throws Exception {
		InventoryItemDto result = InventoryItemDto.fromMap(null);
		assertNotNull(result);
		assertGettersReturnNull(result);
	}

	@Test
	@DisplayName("Empty map returns an empty InventoryItemDto")
	void emptyMap() throws Exception {
		InventoryItemDto result = InventoryItemDto.fromMap(new HashMap<>());
		assertNotNull(result);
		assertGettersReturnNull(result);
	}

	private void assertGettersReturnNull(InventoryItemDto result) throws IllegalAccessException, InvocationTargetException {
		Method[] methods = InventoryItemDto.class.getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("get")) {
				assertNull(m.invoke(result));
			}
		}
	}
}
