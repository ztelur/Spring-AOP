package com.remcarpediem.test.aop.form;

import lombok.Data;

@Data
public class CreateOrderForm {
    private Long userId;
    private Long goodsId;
    private Integer num;
}
