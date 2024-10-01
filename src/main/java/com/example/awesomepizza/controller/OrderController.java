package com.example.awesomepizza.controller;

import com.example.awesomepizza.model.PizzaOrder;
import com.example.awesomepizza.model.PizzaOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface OrderController {


    @PostMapping
    ResponseEntity<PizzaOrder> createOrder(@RequestBody PizzaOrderRequest pizzaOrder);

    @GetMapping("/pending")
    ResponseEntity<List<PizzaOrder>> getPendingOrders();

    @PutMapping("/{id}/status")
    ResponseEntity<PizzaOrder> updateOrderStatus(@PathVariable Long id, @RequestBody PizzaOrder pizzaOrder);

}
