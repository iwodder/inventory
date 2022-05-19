package com.wodder.inventory.domain.model.product;

import java.util.*;

public class ProductId {

	private final String id;

	ProductId() {
		id = UUID.randomUUID().toString();
	}

	private ProductId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProductId)) return false;
		ProductId that = (ProductId) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public static ProductId generateId() {
		return new ProductId();
	}

	public static ProductId productIdOf(String value) {
		return new ProductId(value);
	}
}
