package com.wodder.inventory.persistence;

import java.util.*;

public interface Repository<T> {

	Optional<T> load(Long id);

	void updateItem(T item);

	List<T> loadAllItems();

	T createItem(T item);

	void deleteItem(Long id);

	void deleteItem(T item);
}
