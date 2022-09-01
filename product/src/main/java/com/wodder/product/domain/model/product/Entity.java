package com.wodder.product.domain.model.product;

public abstract class Entity<T> {
  protected Long databaseId;
  protected T id;

  protected Entity(T id) {
    this.id = id;
  }

  public T getId() {
    return id;
  }

  public Long getDatabaseId() {
    return databaseId;
  }
}
