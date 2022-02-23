package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;

public interface Repository<T extends Entity<U>, U> {

	Optional<T> loadById(U id);

	Optional<T> loadByItem(T item);

	void updateItem(T item);

	List<T> loadAllItems();

	T createItem(T item);

	void deleteItem(U id);

	void deleteItem(T item);
}
