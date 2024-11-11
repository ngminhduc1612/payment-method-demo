/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.exception.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.paymentdemo.exception.DuplicatedIdempotentKeyException;
import com.example.paymentdemo.exception.NotEnoughBalanceException;
import com.example.paymentdemo.exception.TooManyRequestException;
import com.example.paymentdemo.exception.UserNotFoundException;

/**
 *
 * @author ADMIN
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_CODE_INTERNAL = "INTERNAL_ERROR";
    private static final Map<Class<? extends RuntimeException>, HttpStatus> EXCEPTION_TO_HTTP_STATUS_CODE = Map.of(
            UserNotFoundException.class, HttpStatus.NOT_FOUND,
            DuplicatedIdempotentKeyException.class, HttpStatus.CONFLICT,
            NotEnoughBalanceException.class, HttpStatus.BAD_REQUEST,
            TooManyRequestException.class, HttpStatus.TOO_MANY_REQUESTS);

    private static final Map<Class<? extends RuntimeException>, String> EXCEPTION_TO_ERROR_CODE = Map.of(
            UserNotFoundException.class, "USER_NOT_FOUND",
            DuplicatedIdempotentKeyException.class, "DUPLICATED_IDEMPOTENT_KEY",
            NotEnoughBalanceException.class, "NOT_ENOUGH_BALANCE",
            TooManyRequestException.class, "TOO_MANY_REQUEST");

    @ExceptionHandler()
    ResponseEntity<ApiExceptionResponse> handleUserNotFoundException(RuntimeException exception) {
        HttpStatus httpStatus = EXCEPTION_TO_HTTP_STATUS_CODE.getOrDefault(exception.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        String errorCode = EXCEPTION_TO_ERROR_CODE.getOrDefault(exception.getClass(), ERROR_CODE_INTERNAL);

        final ApiExceptionResponse response = ApiExceptionResponse.builder().status(httpStatus).errorCode(errorCode)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
