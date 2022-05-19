package com.wodder.inventory.domain.model;

public abstract class Entity<T> {
	protected Long databaseId;
	protected T id;

	protected Entity(T id) {
		this.id = id;
	}

	public T getId() {
		return id;
	}
}
