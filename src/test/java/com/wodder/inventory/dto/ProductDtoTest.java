package com.wodder.inventory.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductDtoTest {

  @Test
  @DisplayName("Creates ProductModel from map")
  void createDtoFromMap() {
    Map<String, String> values = new HashMap<>();
    values.put("NAME", "bread");
    values.put("ID", "1");
    values.put("CATEGORY", "refrigerated");
    ProductDto result = ProductDto.fromMap(values);
    assertNotNull(result);
    assertEquals("1", result.getId());
    assertEquals("bread", result.getName());
    assertEquals("refrigerated", result.getCategory());
  }

  @Test
  @DisplayName("Creates ProductModel from map with missing id")
  void createDtoFromMapMissingId() {
    Map<String, String> values = new HashMap<>();
    values.put("NAME", "bread");
    values.put("CATEGORY", "refrigerated");
    ProductDto result = ProductDto.fromMap(values);
    assertNotNull(result);
    assertNull(result.getId());
    assertEquals("bread", result.getName());
    assertEquals("refrigerated", result.getCategory());
  }

  @Test
  @DisplayName("Null map returns an empty ProductModel")
  void nullMap() throws Exception {
    ProductDto result = ProductDto.fromMap(null);
    assertNotNull(result);
    assertGettersReturnNull(result);
  }

  @Test
  @DisplayName("Empty map returns an empty ProductModel")
  void emptyMap() throws Exception {
    ProductDto result = ProductDto.fromMap(new HashMap<>());
    assertNotNull(result);
    assertGettersReturnNull(result);
  }

  private void assertGettersReturnNull(ProductDto result)
      throws IllegalAccessException, InvocationTargetException {
    Method[] methods = ProductDto.class.getDeclaredMethods();
    for (Method m : methods) {
      if (m.getName().startsWith("get")) {
        assertNullOrZero(m.invoke(result));
      }
    }
  }

  private void assertNullOrZero(Object o) {
    try {
      assertNull(o);
    } catch (AssertionError e) {
      assertEquals(0, o);
    }
  }
}
