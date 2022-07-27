package com.wodder.inventory.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResultTest {

  @Test
  @DisplayName("Can create a result type")
  void result() {
    Result<String, String> r = new Result<>("Ok", null);
    assertNotNull(r);
  }

  @Test
  @DisplayName("If isOkay, then isErr is false")
  void result_1() {
    Result<String, String> r = new Result<>("Ok", null);
    assertTrue(r.isOk());
    assertFalse(r.isErr());
  }

  @Test
  @DisplayName("Creating a result with both err and ok causes exception")
  void result_2() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Result<String, String> r = new Result<>("Ok", "err");
        });
  }

  @Test
  @DisplayName("Illegal argument explains both cannot be present")
  void result_3() {
    try {
      Result<String, String> r = new Result<>("Ok", "err");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot create a result with both types, one must be null", e.getMessage());
    }
  }

  @Test
  @DisplayName("When result is present can access the value")
  void result_4() {
    String ok = "Ok";
    Result<String, String> r = new Result<>(ok, null);
    assertSame(ok, r.getOk());
  }

  @Test
  @DisplayName("When err is present can access the value")
  void result_5() {
    String err = "Err";
    Result<String, String> r = new Result<>(null, err);
    assertSame(err, r.getErr());
  }
}
