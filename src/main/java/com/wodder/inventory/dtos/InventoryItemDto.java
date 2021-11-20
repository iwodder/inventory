package com.wodder.inventory.dtos;

import java.util.*;

public class InventoryItemDto {
	private Long id;
	private String name;
	private String category;

	private InventoryItemDto(InventoryItemDtoBuilder b) {
		this.id = b.id;
		this.name = b.name;
		this.category = b.category;
	}

	private InventoryItemDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public static InventoryItemDto fromMap(Map<String, String> values) {
		InventoryItemDto result = new InventoryItemDto();
		if (values != null && !values.isEmpty()) {
			if (values.get("ID") != null) {
				result.id = Long.parseLong(values.get("ID"));
			}
			result.name = values.get("NAME");
			result.category = values.get("CATEGORY");
		}
		return result;
	}

	public static InventoryItemDtoBuilder builder() {
		return new InventoryItemDtoBuilder();
	}

	public static class InventoryItemDtoBuilder {
		private Long id;
		private String name;
		private String category;

		private InventoryItemDtoBuilder() {
			//no-op
		}

		public InventoryItemDtoBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public InventoryItemDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public InventoryItemDtoBuilder withCategory(String category) {
			this.category = category;
			return this;
		}

		public InventoryItemDto build() {
			return new InventoryItemDto(this);
		}
	}
}
