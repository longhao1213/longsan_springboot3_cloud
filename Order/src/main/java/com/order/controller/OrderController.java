package com.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/getOrder")
    public String getOrder(){
        return "myOrders";
    }

}
