package com.wodder.inventory.console.models;

import com.wodder.inventory.dto.*;

import java.util.*;

public interface InventoryItemModel {

	Result<ProductModel, String> createItem(ProductModel itemDto);

	Result<Boolean, String> deleteItem(ProductModel itemDto);

	Result<ProductModel, String> updateItem(ProductModel itemDto);

	Result<List<ProductModel>, String> getItems();
}
