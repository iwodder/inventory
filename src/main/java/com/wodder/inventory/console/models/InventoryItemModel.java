package com.wodder.inventory.console.models;

import com.wodder.inventory.dto.ProductModel;
import com.wodder.inventory.dto.Result;
import java.util.List;

public interface InventoryItemModel {

  Result<ProductModel, String> createItem(ProductModel itemDto);

  Result<Boolean, String> deleteItem(ProductModel itemDto);

  Result<ProductModel, String> updateItem(ProductModel itemDto);

  Result<List<ProductModel>, String> getItems();
}
