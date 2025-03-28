package io.jeidiiy.sirenordersystem.jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.exception.ErrorResponse;
import io.jeidiiy.sirenordersystem.jwt.exception.IlligalSignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
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
    HttpStatusCode statusCode;
    if (authException instanceof CredentialsExpiredException
        || authException instanceof IlligalSignatureException) {
      statusCode = HttpStatus.UNAUTHORIZED;
    } else {
      statusCode = HttpStatus.BAD_REQUEST;
    }
    ErrorResponse errorResponse = new ErrorResponse(statusCode, authException.getMessage());
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(statusCode.value());
    response.getWriter().write(mapper.writeValueAsString(errorResponse));
  }
}
