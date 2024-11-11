/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.service;

import org.springframework.stereotype.Service;

import com.example.paymentdemo.dto.CreateTransactionRequest;
import com.example.paymentdemo.dto.CreateTransactionResponse;
import com.example.paymentdemo.dto.GetUserTransactionsRequest;
import com.example.paymentdemo.dto.GetUserTransactionsResponse;

/**
 *
 * @author ADMIN
 */
@Service
public interface TransactionService {
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);

    GetUserTransactionsResponse getUserTransactions(GetUserTransactionsRequest request);
}
