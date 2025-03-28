package io.jeidiiy.sirenordersystem.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityMalformedJwtException extends AuthenticationException {
  public SecurityMalformedJwtException(String msg) {
    super(msg);
  }
}
