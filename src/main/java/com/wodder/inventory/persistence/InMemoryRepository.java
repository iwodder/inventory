package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

public abstract class InMemoryRepository<T extends Entity<U>, U> implements Repository<T, U> {
	protected final AtomicLong id = new AtomicLong(1);
	protected final Vector<T> items = new Vector<>();

	@Override
	public final Optional<T> loadById(U id) {
		return items.stream().filter(i -> i.getId().equals(id)).findFirst();
	}

	@Override
	public final Optional<T> loadByItem(T item) {
		Optional<T> opt = items.stream().filter(c -> c.equals(item)).findFirst();
		if (opt.isPresent()) {
			return copy(opt.get());
		} else {
			return opt;
		}
	}

	@Override
	public T createItem(T item) {
		addItem(item);
		return copy(item).orElseThrow(() -> new RuntimeException());
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
	public void deleteItem(U id) {
		//categories cannot be deleted
	}

	@Override
	public void deleteItem(T item) {
		//categories cannot be deleted
	}

	final void addItem(T item) {
		Long dbId;
		try {
			dbId = getNextId();
			Field databaseId = item.getClass().getSuperclass().getDeclaredField("databaseId");
			databaseId.setAccessible(true);
			databaseId.set(item, dbId);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(
					String.format("Field databaseId didn't exist in parent class of %s", item.getClass().getName())
			);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					String.format("Unable to access field databaseId in class of %s", item.getClass().getName())
			);
		} catch (Exception e) {
			throw new RuntimeException(
					String.format("Unexpected exception occurred when setting field, %s", e.getMessage())
			);
		}
		items.add(dbId.intValue() - 1, item);
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
