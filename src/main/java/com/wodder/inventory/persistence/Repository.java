package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;

public interface Repository<T extends Entity> {

	Optional<T> loadById(Long id);

	Optional<T> loadByItem(T item);

	void updateItem(T item);

	List<T> loadAllItems();

	T createItem(T item);

	void deleteItem(Long id);

	void deleteItem(T item);
}
