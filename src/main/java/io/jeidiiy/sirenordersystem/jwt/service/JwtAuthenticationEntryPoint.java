package io.jeidiiy.sirenordersystem.jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import io.jeidiiy.sirenordersystem.exception.ErrorResponse;
import io.jeidiiy.sirenordersystem.jwt.exception.IlligalSignatureException;
import io.jeidiiy.sirenordersystem.jwt.exception.SecurityMalformedJwtException;
import io.jeidiiy.sirenordersystem.jwt.exception.SecurityUnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper mapper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {

    HttpStatusCode statusCode = null;
    ErrorCode errorCode = null;
    if (authException instanceof CredentialsExpiredException) {
      errorCode = ErrorCode.JWT_IS_EXPIRED;
      statusCode = HttpStatus.UNAUTHORIZED;
    } else if (authException instanceof IlligalSignatureException) {
      errorCode = ErrorCode.JWT_SIGNATURE;
      statusCode = HttpStatus.UNAUTHORIZED;
    } else if (authException instanceof SecurityUnsupportedJwtException) {
      errorCode = ErrorCode.JWT_UNSUPPORTED;
      statusCode = HttpStatus.BAD_REQUEST;
    } else if (authException instanceof UsernameNotFoundException) {
      errorCode = ErrorCode.JWT_USERNAME_NOT_FOUND;
      statusCode = HttpStatus.BAD_REQUEST;
    } else if (authException instanceof SecurityMalformedJwtException) {
      errorCode = ErrorCode.JWT_MALFORMED;
      statusCode = HttpStatus.BAD_REQUEST;
    }

    assert errorCode != null;
    ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(statusCode.value());
    response.getWriter().write(mapper.writeValueAsString(errorResponse));
  }
}
