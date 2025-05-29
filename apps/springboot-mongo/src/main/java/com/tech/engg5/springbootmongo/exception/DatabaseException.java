package com.tech.engg5.springbootmongo.exception;

public class DatabaseException extends Exception {

  public DatabaseException(String message) {
    super(message);
  }

  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }
}
