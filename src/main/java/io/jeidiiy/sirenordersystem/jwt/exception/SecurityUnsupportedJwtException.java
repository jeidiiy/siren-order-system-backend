package io.jeidiiy.sirenordersystem.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityUnsupportedJwtException extends AuthenticationException {
  public SecurityUnsupportedJwtException(String msg) {
    super(msg);
  }
}
