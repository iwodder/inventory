package com.wodder.inventory.persistence;


import com.wodder.inventory.domain.model.product.*;

import java.util.*;

public final class InMemoryCategoryRepository extends InMemoryRepository<Category, CategoryId> {

	InMemoryCategoryRepository() {};

	@Override
	public Optional<Category> updateItem(Category item) {
		//no-op
		return Optional.empty();
	}
}
