/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author ADMIN
 */
@Data
@Builder
public class CreateTransactionRequest {
    @Positive
    @NotNull
    private int userId;

    @Positive
    @NotNull
    private int amount;

    @Length(max = 200, min = 100)
    @NotNull
    private String idempotentKey;
}
