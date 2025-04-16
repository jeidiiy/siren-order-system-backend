package io.jeidiiy.sirenordersystem.exception;

import io.jeidiiy.sirenordersystem.jwt.exception.RefreshTokenNotSetException;
import io.jeidiiy.sirenordersystem.user.exception.UserAlreadyExistsException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RefreshTokenNotSetException.class)
  public ResponseEntity<ErrorResponse> handleRefreshTokenNotSetException(
      RefreshTokenNotSetException e) {
    ErrorCode errorCode = e.getErrorCode();
    return new ResponseEntity<>(
        new ErrorResponse(errorCode.getCode(), errorCode.getMessage()), errorCode.getStatus());
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    return new ResponseEntity<>(
        new ErrorResponse(errorCode.getCode(), errorCode.getMessage()), errorCode.getStatus());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorCode errorCode = ErrorCode.ILLEGAL_ARGUMENT;
    return new ResponseEntity<>(
        new ErrorResponse(errorCode.getCode(), e.getMessage()), errorCode.getStatus());
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
      UserAlreadyExistsException e) {
    ErrorCode errorCode = e.getErrorCode();
    return new ResponseEntity<>(
        new ErrorResponse(errorCode.getCode(), errorCode.getMessage()), errorCode.getStatus());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
      UsernameNotFoundException e) {
    ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;
    return new ResponseEntity<>(
        new ErrorResponse(errorCode.getCode(), errorCode.getMessage()), errorCode.getStatus());
  }

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(
      AuthorizationDeniedException e) {
    ErrorCode errorCode = ErrorCode.USER_FORBIDDEN;
    return new ResponseEntity<>(
        new ErrorResponse(errorCode.getCode(), errorCode.getMessage()), errorCode.getStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    var errorMessage =
        e.getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList()
            .toString();
    ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
    return new ResponseEntity<>(
        new ErrorResponse(errorCode.getCode(), errorMessage), errorCode.getStatus());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.internalServerError().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    return ResponseEntity.internalServerError().build();
  }
}
