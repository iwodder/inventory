package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryInventoryRepositoryTest {

	private InMemoryInventoryRepository repo;

	@BeforeEach
	void setup() {
		repo = new InMemoryInventoryRepository();
	}

	@Test
	@DisplayName("Can save inventory to store")
	void save() {
		Inventory i = new Inventory();
		assertNotNull(repo.createItem(i));
	}

	@Test
	@DisplayName("Can load inventory from store")
	void load() {
		Inventory i = new Inventory();
		repo.createItem(i);
		Inventory i2 = repo.loadByItem(i).get();
		assertNotSame(i, i2);
		assertNotNull(i2);
	}


	@Test
	@DisplayName("Loading non-existent inventory returns empty optional")
	void load_non_existent() {
		Inventory i = new Inventory(LocalDate.of(2020, 1, 1));
		Optional<Inventory> result = repo.loadByItem(i);
		assertTrue(result.isEmpty());
	}
}
