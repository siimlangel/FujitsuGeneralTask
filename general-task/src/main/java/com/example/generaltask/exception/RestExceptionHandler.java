package com.example.generaltask.exception;


import com.example.generaltask.error.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Send a more helpful error message to client when request was unreadable.
     * @param ex Exception thrown with bad request
     * @param header Response headers
     * @param status Response status
     * @param webRequest Request
     * @return Response with more helpful info
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders header,
            HttpStatus status, WebRequest webRequest
            ) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex, error));
    }


    /**
     * Send a more helpful error message to client when sending unsupported requests.
     * @param ex Exception thrown with bad request
     * @param headers Response headers
     * @param status Response status
     * @param request Request
     * @return Response with more helpful info
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Executed when db entity constraints are violated with a request.
     * Sends back a more helpful error message.
     * @param ex Exception thrown.
     * @return Response with more heplful info.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Executed when DataIntegrityViolationException is thrown.
     * @param ex exception thrown.
     * @return Response with message about the violation.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex, ex.getMessage());

        return buildResponseEntity(apiError);
    }

    /**
     * Helper method to build responses.
     * @param apiError Custom error for api errors.
     * @return New Response with provided error.
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
