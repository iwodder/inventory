package com.wodder.inventory.console.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.wodder.inventory.application.ProductService;
import com.wodder.inventory.dto.ProductModel;
import com.wodder.inventory.dto.Result;
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

  @Mock ProductService productService;

  @InjectMocks InventoryItemModelImpl inventoryItemModel;

  @Test
  @DisplayName("Create item returns ok on success")
  void createItemSuccess() {
    when(productService.createNewProduct(any(ProductModel.class)))
        .thenReturn(Optional.of(ProductModel.builder().build()));
    Result<ProductModel, String> r = inventoryItemModel.createItem(ProductModel.builder().build());
    assertTrue(r.isOk());
    assertFalse(r.isErr());
  }

  @Test
  @DisplayName("Create item returns error result on failure")
  void createItemFailure() {
    when(productService.createNewProduct(any(ProductModel.class))).thenReturn(Optional.empty());
    Result<ProductModel, String> r = inventoryItemModel.createItem(ProductModel.builder().build());
    assertFalse(r.isOk());
    assertTrue(r.isErr());
  }

  @Test
  @Disabled
  @DisplayName("Delete item returns ok on success")
  void deleteItemSuccess() {
    Result<Boolean, String> result = inventoryItemModel.deleteItem(ProductModel.builder().build());
    assertTrue(result.isOk());
    assertFalse(result.isErr());
  }

  @Test
  @DisplayName("Delete item returns err on failure")
  void deleteItemFailure() {
    Result<Boolean, String> result = inventoryItemModel.deleteItem(ProductModel.builder().build());
    assertTrue(result.isErr());
    assertFalse(result.isOk());
  }

  @Test
  @DisplayName("Update item returns ok on success")
  void updateItemSuccess() {
    when(productService.updateProductCategory(any(), any()))
        .thenReturn(Optional.of(ProductModel.builder().build()));
    Result<ProductModel, String> result =
        inventoryItemModel.updateItem(ProductModel.builder().build());
    assertTrue(result.isOk());
    assertFalse(result.isErr());
  }

  @Test
  @DisplayName("Update item returns err on failure")
  void updateItemFailure() {
    Result<ProductModel, String> result =
        inventoryItemModel.updateItem(ProductModel.builder().build());
    assertTrue(result.isErr());
    assertFalse(result.isOk());
  }

  @Test
  @DisplayName("Get items returns err on failure")
  void getItemsFailure() {
    Result<List<ProductModel>, String> result = inventoryItemModel.getItems();
    assertFalse(result.isOk());
    assertTrue(result.isErr());
  }

  @Test
  @DisplayName("Get items returns err on empty items list")
  void getItemsFailure1() {
    when(productService.loadAllActiveProducts()).thenReturn(Collections.emptyList());
    Result<List<ProductModel>, String> result = inventoryItemModel.getItems();
    assertFalse(result.isOk());
    assertTrue(result.isErr());
  }

  @Test
  @DisplayName("Get items returns ok on success")
  void getItemsSuccess() {
    List<ProductModel> items = new ArrayList<>();
    items.add(ProductModel.builder().build());
    when(productService.loadAllActiveProducts()).thenReturn(items);
    Result<List<ProductModel>, String> result = inventoryItemModel.getItems();
    assertFalse(result.isErr());
    assertTrue(result.isOk());
  }
}
