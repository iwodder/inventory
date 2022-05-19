package com.wodder.inventory.domain.model.product;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

	@Test
	@DisplayName("Category defaults to unassigned")
	void name() {
		Category c = new Category();
		assertEquals("unassigned", c.getName());
	}

	@Test
	@DisplayName("Category can be assigned name during construction")
	void assign_name() {
		Category c = new Category("pantry");
		assertEquals("pantry", c.getName());
	}

	@Test
	@DisplayName("Category must be created with non-empty string")
	void not_blank() {
		Category c = new Category("");
		assertEquals("unassigned", c.getName());
	}
}