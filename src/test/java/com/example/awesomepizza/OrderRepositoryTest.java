package com.example.awesomepizza;

import com.example.awesomepizza.model.PizzaOrder;
import com.example.awesomepizza.repository.PizzaOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private PizzaOrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        PizzaOrder order1 = new PizzaOrder();
        order1.setPizzaType("Margherita");
        order1.setStatus("IN_CORSO");
        order1.setOrderCode("12345");

        PizzaOrder order2 = new PizzaOrder();
        order2.setPizzaType("Pepperoni");
        order2.setStatus("PRONTO");
        order2.setOrderCode("12346");

        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @Test
    public void findByStatus_ShouldReturnOrders_WhenStatusIsInCorso() {
        List<PizzaOrder> orders = orderRepository.findByStatus("IN_CORSO");

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getPizzaType()).isEqualTo("Margherita");
    }

    @Test
    public void findByStatus_ShouldReturnOrders_WhenStatusIsPronto() {
        List<PizzaOrder> orders = orderRepository.findByStatus("PRONTO");

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getPizzaType()).isEqualTo("Pepperoni");
    }

    @Test
    public void findByStatus_ShouldReturnEmptyList_WhenStatusIsUnknown() {
        List<PizzaOrder> orders = orderRepository.findByStatus("UNKNOWN");

        assertThat(orders).isEmpty();
    }
}
