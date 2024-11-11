/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public interface  DistributedLockService {
    boolean accquireLock(String idempotentKey);
    void releaseLock(String idempotentKey);
}
