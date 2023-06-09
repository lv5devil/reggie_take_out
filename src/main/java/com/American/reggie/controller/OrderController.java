package com.American.reggie.controller;

import com.American.reggie.common.R;
import com.American.reggie.entity.Orders;
import com.American.reggie.service.OrderDetailService;
import com.American.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String>submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("提交订单成功");
    }
}
