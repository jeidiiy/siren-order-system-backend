package io.jeidiiy.sirenordersystem.jwt.exception;

public class ExpiredRefreshTokenException extends RuntimeException {
  public ExpiredRefreshTokenException(String message) {
    super(message);
  }
}
