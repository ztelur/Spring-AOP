package com.remcarpediem.test.aop.service;

import com.remcarpediem.test.aop.aop.advisor.Log;
import com.remcarpediem.test.aop.aop.advisor.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Log(value = LogLevel.ERROR, isTest = true)
    @Transactional
    public Long createOrder(Long userId, Long goodsId, Integer num) {
        return 1L;
    }
}
