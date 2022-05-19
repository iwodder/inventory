package com.wodder.inventory.console.models;

import com.wodder.inventory.application.*;
import com.wodder.inventory.dto.*;

import java.util.*;

public class InventoryItemModelImpl implements InventoryItemModel {
	private final ProductService storage;

	public InventoryItemModelImpl(ProductService productService) {
		this.storage = productService;
	}

	@Override
	public Result<ProductModel, String> createItem(ProductModel itemDto) {
		Optional<ProductModel> result = storage.createNewProduct(itemDto);
		return result.<Result<ProductModel, String>>map(inventoryItemDto -> new Result<>(inventoryItemDto, null))
				.orElseGet(() -> new Result<>(null, "Unable to create new item"));
	}

	@Override
	public Result<Boolean, String> deleteItem(ProductModel itemDto) {
		boolean result = storage.deleteProduct(itemDto.getId());
		if (result) {
			return new Result<>(Boolean.TRUE, null);
		} else {
			return new Result<>(null, "Unable to delete item");
		}
	}

	@Override
	public Result<ProductModel, String> updateItem(ProductModel itemDto) {
		Optional<ProductModel> result = storage.updateProductCategory(itemDto.getId(), itemDto.getCategory());
		return result.<Result<ProductModel, String>>map(i -> new Result<>(i, null))
				.orElseGet(() -> new Result<>(null, "Unable to update item"));
	}

	@Override
	public Result<List<ProductModel>, String> getItems() {
		List<ProductModel> items = storage.loadAllActiveProducts();
		return items.isEmpty() ?
				new Result<>(null, "Unable to access items") :
				new Result<>(items, null);
	}
}
