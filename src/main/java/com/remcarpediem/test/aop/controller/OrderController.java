package com.remcarpediem.test.aop.controller;

import com.remcarpediem.test.aop.aop.proxy.Log;
import com.remcarpediem.test.aop.aop.proxy.LogLevel;
import com.remcarpediem.test.aop.form.CreateOrderForm;
import com.remcarpediem.test.aop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/order")
    @Log(value = LogLevel.ERROR)
    public Long createOrder(@RequestBody CreateOrderForm form) {
        Long id = orderService.createOrder(form.getUserId(),
                form.getGoodsId(), form.getNum());
        return id;
    }
}
