package com.example.awesomepizza.repository;

import com.example.awesomepizza.model.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {
    List<PizzaOrder> findByStatus(String status);
}
