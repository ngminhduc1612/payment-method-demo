/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "idempotent_key", unique = true, nullable = false, length = 200)
    private String idempotentKey;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "balance_before_transaction", nullable = false)
    private int balanceBeforeTransaction;

    @Column(name = "balance_after_transaction", nullable = false)
    private int balanceAfterTransaction;

    @CreationTimestamp
    private Instant createdOn;

}
