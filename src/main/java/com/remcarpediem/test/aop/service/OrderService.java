package com.remcarpediem.test.aop.service;

import com.remcarpediem.test.aop.aop.proxy.Log;
import com.remcarpediem.test.aop.aop.proxy.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Log(value = LogLevel.ERROR, isTest = true)
    public Long createOrder(Long userId, Long goodsId, Integer num) {
        return 1L;
    }
}
