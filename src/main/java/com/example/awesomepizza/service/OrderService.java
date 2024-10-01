package com.example.awesomepizza.service;

import com.example.awesomepizza.model.PizzaOrder;
import com.example.awesomepizza.model.PizzaOrderRequest;
import com.example.awesomepizza.repository.PizzaOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private PizzaOrderRepository orderRepository;

    public PizzaOrder createOrder(PizzaOrderRequest request) {
        PizzaOrder order = new PizzaOrder();
        order.setPizzaType(request.getPizzaType());
        order.setStatus("IN_CORSO");
        order.setOrderCode(generateOrderCode());
        return orderRepository.save(order);
    }

    public List<PizzaOrder> getPendingOrders() {
        return orderRepository.findByStatus("IN_CORSO");
    }
    public PizzaOrder updateOrderStatus(Long id, String status) {
        PizzaOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    private String generateOrderCode() {
        return UUID.randomUUID().toString();
    }
}
