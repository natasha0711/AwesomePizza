package com.example.awesomepizza;

import com.example.awesomepizza.controller.OrderControllerImpl;
import com.example.awesomepizza.model.PizzaOrder;
import com.example.awesomepizza.model.PizzaOrderRequest;
import com.example.awesomepizza.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AwesomePizzaApplicationTests {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderControllerImpl orderController;

    private Long orderId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderId = 1L;
    }

    @Test
    public void createOrder_ShouldReturnCreatedOrder() {
        PizzaOrder orderToReturn = new PizzaOrder();
        orderToReturn.setId(orderId);
        orderToReturn.setPizzaType("Margherita");
        orderToReturn.setStatus("IN_CORSO");
        orderToReturn.setOrderCode("12345");

        PizzaOrderRequest request = new PizzaOrderRequest("Margherita");

        when(orderService.createOrder(any(PizzaOrderRequest.class))).thenReturn(orderToReturn);

        ResponseEntity<PizzaOrder> response = orderController.createOrder(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPizzaType()).isEqualTo("Margherita");
        assertThat(response.getBody().getId()).isEqualTo(orderId);
    }

    @Test
    public void getPendingOrders_ShouldReturnListOfOrders() {
        PizzaOrder order = new PizzaOrder();
        order.setId(orderId);
        order.setPizzaType("Margherita");
        order.setStatus("IN_CORSO");
        order.setOrderCode("12345");

        when(orderService.getPendingOrders()).thenReturn(Collections.singletonList(order));

        ResponseEntity<List<PizzaOrder>> response = orderController.getPendingOrders();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getPizzaType()).isEqualTo("Margherita");
    }

    @Test
    public void updateOrderStatus_ShouldToggleOrderStatus() {
        PizzaOrder existingOrder = new PizzaOrder();
        existingOrder.setId(orderId);
        existingOrder.setPizzaType("Margherita");
        existingOrder.setStatus("IN_CORSO");
        existingOrder.setOrderCode("12345");

        when(orderService.getPendingOrders()).thenReturn(Collections.singletonList(existingOrder));

        ResponseEntity<List<PizzaOrder>> getResponse = orderController.getPendingOrders();
        PizzaOrder orderToUpdate = getResponse.getBody().get(0);

        String newStatus = "PRONTO".equals(orderToUpdate.getStatus()) ? "IN_CORSO" : "PRONTO";

        when(orderService.updateOrderStatus(orderId, newStatus)).thenAnswer(invocation -> {
            orderToUpdate.setStatus(newStatus);
            return orderToUpdate;
        });

        PizzaOrder requestOrder = new PizzaOrder();
        requestOrder.setId(orderId);
        requestOrder.setPizzaType(orderToUpdate.getPizzaType());
        requestOrder.setOrderCode(orderToUpdate.getOrderCode());
        requestOrder.setStatus(newStatus);

        ResponseEntity<PizzaOrder> response = orderController.updateOrderStatus(orderId, requestOrder);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(newStatus);
    }

}
