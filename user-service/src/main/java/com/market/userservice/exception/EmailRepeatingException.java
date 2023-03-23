package com.market.userservice.exception;

public class EmailRepeatingException extends RuntimeException {

  @Override
  public String getMessage() {
    return "Email already exist";
  }
}
