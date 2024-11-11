/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.paymentdemo.dto.CreateTransactionRequest;
import com.example.paymentdemo.dto.CreateTransactionResponse;
import com.example.paymentdemo.dto.GetUserTransactionsRequest;
import com.example.paymentdemo.dto.GetUserTransactionsResponse;
import com.example.paymentdemo.exception.DuplicatedIdempotentKeyException;
import com.example.paymentdemo.exception.NotEnoughBalanceException;
import com.example.paymentdemo.exception.TooManyRequestException;
import com.example.paymentdemo.exception.UserNotFoundException;
import com.example.paymentdemo.model.Transaction;
import com.example.paymentdemo.model.User;
import com.example.paymentdemo.repository.TransactionRepository;
import com.example.paymentdemo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ADMIN
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final DistributedLockService distributedLockService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        CreateTransactionResponse createTransactionResponse;
        if (distributedLockService.accquireLock(request.getIdempotentKey())) {
            log.info("accquired lock successfully");
            try {
                Optional<Transaction> optionalExistedTransaction = transactionRepository
                        .findOneByIdempotentKey(request.getIdempotentKey());
                log.info("optionalExistedTransaction={}", optionalExistedTransaction);
                if (optionalExistedTransaction.isPresent()) {
                    Transaction existedTransaction = optionalExistedTransaction.get();
                    if (existedTransaction.getUserId() == request.getUserId()
                            && existedTransaction.getAmount() == request.getAmount()) {
                        createTransactionResponse = CreateTransactionResponse.builder()
                                .transactionId(existedTransaction.getId())
                                .remainBalance(existedTransaction.getBalanceAfterTransaction()).build();
                    } else {
                        throw new DuplicatedIdempotentKeyException();
                    }
                } else { // chưa có impodentKey thì cần khởi tạo transaction mới
                    final User user = userRepository.findOneWithLockingById(request.getUserId())
                            .orElseThrow(UserNotFoundException::new);
                    if (user.getBalance() < request.getAmount()) {
                        throw new NotEnoughBalanceException();
                    }

                    // Nếu tài khoản còn đủ => tiến hành giao dịch: trừ tiền và tạo lịch sử
                    // transaction
                    int balanceBeforeTransaction = user.getBalance();
                    int balanceAfterTransaction = user.getBalance() - request.getAmount();
                    user.setBalance(balanceAfterTransaction);
                    userRepository.save(user);
                    Transaction transaction = transactionRepository.save(Transaction.builder()
                            .idempotentKey(request.getIdempotentKey())
                            .userId(request.getUserId())
                            .amount(request.getAmount())
                            .balanceBeforeTransaction(balanceBeforeTransaction)
                            .balanceAfterTransaction(balanceAfterTransaction)
                            .build());
                    createTransactionResponse = CreateTransactionResponse.builder().transactionId(transaction.getId())
                            .remainBalance(balanceAfterTransaction)
                            .build();
                }
            } finally {
                distributedLockService.releaseLock(request.getIdempotentKey());
                log.info("release lock succesfully");
            }
        } else {
            log.info("cannot accquired lock");
            throw new TooManyRequestException();
        }
        return createTransactionResponse;
    }

    @Override
    public GetUserTransactionsResponse getUserTransactions(GetUserTransactionsRequest request) {
        return GetUserTransactionsResponse.builder()
                .transactions(transactionRepository.findByUserId(request.getUserId()))
                .build();
    }

}
