package com.wodder.console.exceptions;

public class UnknownMenuException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UnknownMenuException(String message) {
    super(message);
  }
}
