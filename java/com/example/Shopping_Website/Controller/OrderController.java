package com.example.Shopping_Website.Controller;

import com.example.Shopping_Website.Models.Order;
import com.example.Shopping_Website.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")

public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/new/user/{userid}")
    public Order createNewOrder(@RequestBody Order order, @PathVariable Integer userid){
        return service.createNewOrder(order,userid);
    }

    @GetMapping("/{id}")
    public Order viewOrderById(@PathVariable Integer id){
        return service.viewOrderById(id);
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelOrderById(@PathVariable Integer id ){
        service.cancelOrderById(id);
    }
}
