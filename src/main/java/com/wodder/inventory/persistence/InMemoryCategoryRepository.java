package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public final class InMemoryCategoryRepository extends InMemoryRepository<Category> {

	@Override
	public Category createItem(Category item) {
		Category c = new Category(getNextId(), item.getName());
		addItem(c);
		return c;
	}
}
