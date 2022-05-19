package com.wodder.inventory.persistence;


import com.wodder.inventory.domain.model.*;

import java.util.*;

public interface Repository<T extends Entity<U>, U> {

	Optional<T> loadById(U id);

	Optional<T> loadByItem(T item);

	Optional<T> updateItem(T item);

	List<T> loadAllItems();

	T createItem(T item);

	boolean deleteItem(U id);

	boolean deleteItem(T item);
}
