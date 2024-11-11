/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.service;

import org.springframework.stereotype.Service;

import com.example.paymentdemo.dto.GetUserBalanceRequest;
import com.example.paymentdemo.dto.GetUserBalanceResponse;
import com.example.paymentdemo.exception.UserNotFoundException;
import com.example.paymentdemo.model.User;
import com.example.paymentdemo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ADMIN
 */
@Slf4j
@Service
@RequiredArgsConstructor // khởi tạo contructors với các trường final ngay từ đầu
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request) {
        final User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        return GetUserBalanceResponse.builder().balance(user.getBalance()).build();
    }
}
