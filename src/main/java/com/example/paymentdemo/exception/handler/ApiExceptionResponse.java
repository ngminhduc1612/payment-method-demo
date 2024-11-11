/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.exception.handler;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiExceptionResponse {
    private HttpStatus status;
    private String errorCode;
}
