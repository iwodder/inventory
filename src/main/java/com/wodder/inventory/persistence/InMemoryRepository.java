package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.Entity;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
  public Optional<T> updateItem(T item) {
    int idx = items.indexOf(item);
    if (idx > -1) {
      items.removeElementAt(idx);
      items.insertElementAt(item, idx);
      return copy(item);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public final List<T> loadAllItems() {
    return items.stream()
        .map(this::copy)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public boolean deleteItem(U id) {
    return items.removeIf(item -> item.getId().equals(id));
  }

  @Override
  public boolean deleteItem(T item) {
    return items.removeIf(i -> i.equals(item));
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
          String.format(
              "Field databaseId didn't exist in parent class of %s", item.getClass().getName()));
    } catch (IllegalAccessException e) {
      throw new RuntimeException(
          String.format(
              "Unable to access field databaseId in class of %s", item.getClass().getName()));
    } catch (Exception e) {
      throw new RuntimeException(
          String.format("Unexpected exception occurred when setting field, %s", e.getMessage()));
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
      // TODO log warn
    }
    return Optional.empty();
  }
}
