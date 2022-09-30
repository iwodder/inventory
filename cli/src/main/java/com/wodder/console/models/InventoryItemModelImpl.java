package com.wodder.console.models;


import com.wodder.inventory.dto.Result;
import com.wodder.product.application.ProductService;
import com.wodder.product.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public class InventoryItemModelImpl implements InventoryItemModel {
  private final ProductService storage;

  public InventoryItemModelImpl(ProductService productService) {
    this.storage = productService;
  }

  @Override
  public Result<ProductDto, String> createItem(ProductDto itemDto) {
    Optional<ProductDto> result = storage.createNewProduct(itemDto);
    return result
        .<Result<ProductDto, String>>map(inventoryItemDto -> new Result<>(inventoryItemDto, null))
        .orElseGet(() -> new Result<>(null, "Unable to create new item"));
  }

  @Override
  public Result<Boolean, String> deleteItem(ProductDto itemDto) {
    boolean result = storage.deleteProduct(itemDto.getId());
    if (result) {
      return new Result<>(Boolean.TRUE, null);
    } else {
      return new Result<>(null, "Unable to delete item");
    }
  }

  @Override
  public Result<ProductDto, String> updateItem(ProductDto itemDto) {
    Optional<ProductDto> result =
        storage.updateProductCategory(itemDto.getId(), itemDto.getCategory());
    return result
        .<Result<ProductDto, String>>map(i -> new Result<>(i, null))
        .orElseGet(() -> new Result<>(null, "Unable to update item"));
  }

  @Override
  public Result<List<ProductDto>, String> getItems() {
    List<ProductDto> items = storage.loadAllProducts();
    return items.isEmpty()
        ? new Result<>(null, "Unable to access items")
        : new Result<>(items, null);
  }
}
