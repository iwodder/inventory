package com.wodder.console.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.wodder.inventory.dto.Result;
import com.wodder.product.application.ProductService;
import com.wodder.product.dto.ProductDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductModelImplTest {

  @Mock
  ProductService productService;

  @InjectMocks InventoryItemModelImpl inventoryItemModel;

  @Test
  @DisplayName("Create item returns ok on success")
  void createItemSuccess() {
    when(productService.createNewProduct(any(ProductDto.class)))
        .thenReturn(Optional.of(ProductDto.builder().build()));
    Result<ProductDto, String> r = inventoryItemModel.createItem(ProductDto.builder().build());
    assertTrue(r.isOk());
    assertFalse(r.isErr());
  }

  @Test
  @DisplayName("Create item returns error result on failure")
  void createItemFailure() {
    when(productService.createNewProduct(any(ProductDto.class))).thenReturn(Optional.empty());
    Result<ProductDto, String> r = inventoryItemModel.createItem(ProductDto.builder().build());
    assertFalse(r.isOk());
    assertTrue(r.isErr());
  }

  @Test
  @Disabled
  @DisplayName("Delete item returns ok on success")
  void deleteItemSuccess() {
    Result<Boolean, String> result = inventoryItemModel.deleteItem(ProductDto.builder().build());
    assertTrue(result.isOk());
    assertFalse(result.isErr());
  }

  @Test
  @DisplayName("Delete item returns err on failure")
  void deleteItemFailure() {
    Result<Boolean, String> result = inventoryItemModel.deleteItem(ProductDto.builder().build());
    assertTrue(result.isErr());
    assertFalse(result.isOk());
  }

  @Test
  @DisplayName("Update item returns ok on success")
  void updateItemSuccess() {
    when(productService.updateProductCategory(any(), any()))
        .thenReturn(Optional.of(ProductDto.builder().build()));
    Result<ProductDto, String> result =
        inventoryItemModel.updateItem(ProductDto.builder().build());
    assertTrue(result.isOk());
    assertFalse(result.isErr());
  }

  @Test
  @DisplayName("Update item returns err on failure")
  void updateItemFailure() {
    Result<ProductDto, String> result =
        inventoryItemModel.updateItem(ProductDto.builder().build());
    assertTrue(result.isErr());
    assertFalse(result.isOk());
  }

  @Test
  @DisplayName("Get items returns err on failure")
  void getItemsFailure() {
    Result<List<ProductDto>, String> result = inventoryItemModel.getItems();
    assertFalse(result.isOk());
    assertTrue(result.isErr());
  }

  @Test
  @DisplayName("Get items returns err on empty items list")
  void getItemsFailure1() {
    when(productService.loadAllActiveProducts()).thenReturn(Collections.emptyList());
    Result<List<ProductDto>, String> result = inventoryItemModel.getItems();
    assertFalse(result.isOk());
    assertTrue(result.isErr());
  }

  @Test
  @DisplayName("Get items returns ok on success")
  void getItemsSuccess() {
    List<ProductDto> items = new ArrayList<>();
    items.add(ProductDto.builder().build());
    when(productService.loadAllActiveProducts()).thenReturn(items);
    Result<List<ProductDto>, String> result = inventoryItemModel.getItems();
    assertFalse(result.isErr());
    assertTrue(result.isOk());
  }
}
