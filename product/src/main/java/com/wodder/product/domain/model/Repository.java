package com.wodder.product.domain.model;

import com.wodder.product.domain.model.product.Entity;
import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity<U>, U> {

  Optional<T> loadById(U id);

  Optional<T> loadByItem(T item);

  Optional<T> updateItem(T item);

  List<T> loadAllItems();

  T createItem(T item);

  boolean deleteItem(U id);

  boolean deleteItem(T item);
}
