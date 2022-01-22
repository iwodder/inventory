package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public abstract class InMemoryRepository<T extends Entity> implements Repository<T> {
	protected static final AtomicLong id = new AtomicLong(1);
	protected final Vector<T> items = new Vector<>();

	@Override
	public final Optional<T> loadById(Long id) {
		if ((id - 1) < items.size()) {
			return Optional.of(items.get(id.intValue() - 1));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public final Optional<T> loadByItem(T item) {
		return items.stream().filter(c -> c.equals(item)).findFirst();
	}

	@Override
	public void updateItem(T item) {
		//no-op
	}

	@Override
	public final List<T> loadAllItems() {
		return Collections.unmodifiableList(items);
	}

	@Override
	public void deleteItem(Long id) {
		//categories cannot be deleted
	}

	@Override
	public void deleteItem(T item) {
		//categories cannot be deleted
	}

	final void addItem(T item) {
		items.add(item.getId().intValue() - 1, item);
	}

	final long getNextId() {
		return id.getAndIncrement();
	}
}
