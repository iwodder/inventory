package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public class InMemoryCategoryRepository implements Repository<Category> {
	private static final AtomicLong id = new AtomicLong(1);
	private static final Vector<Category> items = new Vector<>();

	@Override
	public Optional<Category> load(Long id) {
		if ((id - 1) < items.size()) {
			return Optional.of(items.get(id.intValue() - 1));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void updateItem(Category item) {
		//no-op
	}

	@Override
	public List<Category> loadAllItems() {
		return Collections.unmodifiableList(items);
	}

	@Override
	public Category createItem(Category item) {
		Category c = new Category(id.getAndIncrement(), item.getName());
		items.add(c.getId().intValue() - 1, c);
		return c;
	}

	@Override
	public void deleteItem(Long id) {
		//categories cannot be deleted
	}

	@Override
	public void deleteItem(Category item) {
		//categories cannot be deleted
	}
}
