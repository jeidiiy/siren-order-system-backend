package io.jeidiiy.sirenordersystem.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatusCode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(HttpStatusCode status, String message) {}
