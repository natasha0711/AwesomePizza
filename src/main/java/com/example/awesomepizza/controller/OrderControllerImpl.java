package com.example.awesomepizza.controller;

import com.example.awesomepizza.model.PizzaOrder;
import com.example.awesomepizza.model.PizzaOrderRequest;
import com.example.awesomepizza.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderControllerImpl implements OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Override
    public ResponseEntity<PizzaOrder> createOrder(@RequestBody PizzaOrderRequest pizzaOrder) {
        PizzaOrder newOrder = orderService.createOrder(pizzaOrder);
        return ResponseEntity.status(201).body(newOrder);
    }

    @Override
    @GetMapping("/pending")
    public ResponseEntity<List<PizzaOrder>> getPendingOrders() {
        List<PizzaOrder> orders = orderService.getPendingOrders();
        return ResponseEntity.ok(orders);
    }


    @PutMapping("/{id}/status")
    @Override
    public ResponseEntity<PizzaOrder> updateOrderStatus(@PathVariable Long id, @RequestBody PizzaOrder pizzaOrder) {
        PizzaOrder updatedOrder = orderService.updateOrderStatus(id, pizzaOrder.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }


}
