package com.wodder.inventory.console.models;

import com.wodder.inventory.dto.ProductDto;
import com.wodder.inventory.dto.Result;
import java.util.List;

public interface InventoryItemModel {

  Result<ProductDto, String> createItem(ProductDto itemDto);

  Result<Boolean, String> deleteItem(ProductDto itemDto);

  Result<ProductDto, String> updateItem(ProductDto itemDto);

  Result<List<ProductDto>, String> getItems();
}
