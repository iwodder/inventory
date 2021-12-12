package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryInventoryStorageTest {

	private InventoryStorage store;

	@BeforeEach
	void setup() {
		store = new InMemoryInventoryStorage();
	}

	@Test
	@DisplayName("Can save inventory to store")
	void save() {
		Inventory i = new Inventory();
		assertTrue(store.save(i));
	}

	@Test
	@DisplayName("Cannot save inventory with same date")
	void save_same_date() {
		Inventory i = new Inventory();
		Inventory i2 = new Inventory();
		assertTrue(store.save(i));
		assertFalse(store.save(i2));
	}

	@Test
	@DisplayName("Can load inventory from store")
	void load() {
		Inventory i = new Inventory();
		store.save(i);
		Inventory i2 = store.load(i).get();
		assertNotSame(i, i2);
		assertNotNull(i2);
	}

	@Test
	@DisplayName("Loads an inventory based off date")
	void load_by_date() {
		Inventory i = new Inventory(LocalDate.of(2021, 11, 28));
		Inventory i2 = new Inventory();
		store.save(i);
		store.save(i2);
		Inventory result = store.load(i).get();
		assertNotSame(i, result);
		assertNotNull(result);
	}

	@Test
	@DisplayName("Loading non-existent inventory returns empty optional")
	void load_non_existent() {
		Inventory i = new Inventory(LocalDate.of(2020, 1, 1));
		Optional<Inventory> result = store.load(i);
		assertTrue(result.isEmpty());
	}
}
