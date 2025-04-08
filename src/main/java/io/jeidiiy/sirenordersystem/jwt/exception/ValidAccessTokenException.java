package io.jeidiiy.sirenordersystem.jwt.exception;

public class ValidAccessTokenException extends RuntimeException {
  public ValidAccessTokenException(String message) {
    super(message);
  }
}
