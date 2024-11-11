/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.service;

import com.example.paymentdemo.dto.GetUserBalanceRequest;
import com.example.paymentdemo.dto.GetUserBalanceResponse;

/**
 *
 * @author ADMIN
 */
public interface UserService {
    GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request);
}
