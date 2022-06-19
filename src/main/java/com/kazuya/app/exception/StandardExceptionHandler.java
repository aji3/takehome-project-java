package com.kazuya.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class StandardExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleMyException(Exception exception, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();

    exception.printStackTrace();

    ErrorResponseBody errorResponseBody = createErrorResponseBody(exception, request);
    return super.handleExceptionInternal(
        exception,
        errorResponseBody,
        headers,
        HttpStatus.valueOf(errorResponseBody.getStatusCode()),
        request);
  }

  private ErrorResponseBody createErrorResponseBody(Exception exception, WebRequest request) {
    if (exception instanceof StandardException) {
      StandardException standardException = (StandardException) exception;
      return new ErrorResponseBody(standardException.getMessage(), standardException.getStatusCode());
    } else {
      return new ErrorResponseBody(exception.getMessage(), 500);
    }
  }

  public static class ErrorResponseBody {
    private String message;
    private int statusCode;

    public ErrorResponseBody(String message, int statusCode) {
      this.message = message;
      this.statusCode = statusCode;
    }

    public String getMessage() {
      return message;
    }

    public int getStatusCode() {
      return statusCode;
    }
  }
}
