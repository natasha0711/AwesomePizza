package com.example.awesomepizza.model;

public class PizzaOrderRequest {
    private String pizzaType;

    public PizzaOrderRequest() {
    }

    public PizzaOrderRequest(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }
}
