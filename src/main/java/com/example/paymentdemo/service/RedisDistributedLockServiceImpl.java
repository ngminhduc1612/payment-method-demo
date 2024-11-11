/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.paymentdemo.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class RedisDistributedLockServiceImpl implements DistributedLockService {
    private static final int LOCK_TIME_IN_MINUTE = 3;
    private static final String LOCK_VALUE = "1";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String generateLockKey(String idempotenKey) {
        // lockKey = lock:recept_1
        return String.format("lock:%s", idempotenKey);
    }

    @Override
    public boolean accquireLock(String idempotentKey) {
        String lockKey = generateLockKey(idempotentKey);
        return redisTemplate.opsForValue().setIfAbsent(lockKey, LOCK_VALUE, Duration.ofMinutes(LOCK_TIME_IN_MINUTE));
    }

    @Override
    public void releaseLock(String idempotentKey) {
        String lockKey = generateLockKey(idempotentKey);
        redisTemplate.delete(lockKey);
    }

}
