/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.paymentdemo.dto.GetUserBalanceRequest;
import com.example.paymentdemo.dto.GetUserBalanceResponse;
import com.example.paymentdemo.service.UserService;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author ADMIN
 */
@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("{id}/balance")
    public ResponseEntity<GetUserBalanceResponse> getUserBalance(@PathVariable Integer id) {

        final GetUserBalanceResponse registrationResponse = userService
                .getUserBalance(GetUserBalanceRequest.builder().userId(id).build());

        return ResponseEntity.status(HttpStatus.OK).body(registrationResponse);
    }
}
