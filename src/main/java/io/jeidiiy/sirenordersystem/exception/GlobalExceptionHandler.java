package io.jeidiiy.sirenordersystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ClientErrorException.class)
  public ResponseEntity<ErrorResponse> handleClientErrorException(ClientErrorException e) {
    return new ResponseEntity<>(new ErrorResponse(e.getStatus(), e.getMessage()), e.getStatus());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
      UsernameNotFoundException e) {
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    var errorMessage =
        e.getFieldErrors().stream()
            .map(fieldError -> (fieldError.getField() + ": " + fieldError.getDefaultMessage()))
            .toList()
            .toString();
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
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
