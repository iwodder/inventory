package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

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
		return items.stream().map(this::copy)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toUnmodifiableList());
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

	private Optional<T> copy(T other) {
		try {
			Constructor<?> c = other.getClass().getConstructor(other.getClass());
			@SuppressWarnings("unchecked")
			T newInstance = (T) c.newInstance(other);
			return Optional.of(newInstance);
		} catch (Exception e) {
			e.printStackTrace();
			//TODO log warn
		}
		return Optional.empty();
	}
}
