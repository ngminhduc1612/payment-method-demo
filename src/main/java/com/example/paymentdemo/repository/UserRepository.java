/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.example.paymentdemo.model.User;

import jakarta.persistence.LockModeType;

/**
 *
 * @author ADMIN
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findOneWithLockingById(int id);
}
