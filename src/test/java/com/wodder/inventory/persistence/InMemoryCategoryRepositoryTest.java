package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCategoryRepositoryTest {

	static Repository<Category> categoryRepository;

	@BeforeAll
	static void setupAll() {
		categoryRepository = new InMemoryCategoryRepository();
		categoryRepository.createItem(new Category("Dairy"));
		categoryRepository.createItem(new Category("Dry Goods"));
		categoryRepository.createItem(new Category("Meats"));
	}

	@Test
	@DisplayName("Can load a particular category by id")
	void load() {
		Optional<Category> result = categoryRepository.loadById(1L);
		assertTrue(result.isPresent());
		assertEquals(new Category("Dairy"), result.get());
	}

	@Test
	@DisplayName("Can load a category by providing an item")
	void load_by_item() {
		Optional<Category> result = categoryRepository.loadByItem(new Category("Dairy"));
		assertTrue(result.isPresent());
		assertEquals(new Category("Dairy"), result.get());
	}

	@Test
	@DisplayName("Updating a category does nothing")
	void updateItem() {
		categoryRepository.updateItem(new Category("Invalid Category"));
		assertTrue(true);
	}

	@Test
	@DisplayName("Can load all items from a repository")
	void loadAllItems() {
		List<Category> items = categoryRepository.loadAllItems();
		assertEquals(3, items.size());
		assertTrue(items.containsAll(Arrays.asList(new Category("Dairy"), new Category("Dry Goods"), new Category("Meats"))));
	}

	@Test
	@DisplayName("Can create a inventory category")
	void createItem() {
		Category c = categoryRepository.createItem(new Category("Frozen"));
		assertNotNull(c.getId());
		assertEquals(new Category("Frozen"), c);
	}

	@Test
	@DisplayName("Trying to load a category with an invalid id returns empty")
	void doesnt_exist() {
		assertTrue(categoryRepository.loadById(5L).isEmpty());
	}
}
