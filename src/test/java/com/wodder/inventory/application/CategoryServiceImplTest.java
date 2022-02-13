package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

	@Mock
	Repository<Category> categoryRepository;

	@InjectMocks
	CategoryServiceImpl service;

	@Test
	@DisplayName("Is able to create a category")
	void createCategory() {
		when(categoryRepository.createItem(any())).thenReturn(new Category(1L, "Dry Goods"));
		CategoryModel model = service.createCategory("Dry Goods").get();
		assertNotNull(model);
		assertNotNull(model.getId());
		assertEquals("Dry Goods", model.getName());
	}

	@Test
	@DisplayName("If item cannot be created then empty optional is returned")
	void create_category_fails() {
		when(categoryRepository.createItem(any())).thenReturn(null);
		assertTrue(service.createCategory("Dry Goods").isEmpty());
	}

	@Test
	@DisplayName("Loads a category that exists")
	void loadCategory() {
		when(categoryRepository.loadById(1L)).thenReturn(Optional.of(new Category(1L, "Dry Goods")));
		CategoryModel result = service.loadCategory("1").get();
		assertEquals(1L, result.getId());
		assertEquals("Dry Goods", result.getName());
	}

	@Test
	@DisplayName("Handles an id which cannot be parsed to a long")
	void not_parseable() {
		assertTrue(service.loadCategory("a").isEmpty());
	}

	@Test
	@DisplayName("If category cannot be loaded empty optional is returned")
	void no_category() {
		when(categoryRepository.loadById(any())).thenReturn(Optional.empty());
		assertTrue(service.loadCategory("1").isEmpty());
	}
}