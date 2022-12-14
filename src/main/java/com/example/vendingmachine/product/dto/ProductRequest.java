package com.example.vendingmachine.product.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductRequest {

    private int amountAvailable;
    private String name;
    private Double price;

    public ProductRequest() {
    }

    public ProductRequest(int amountAvailable, String name, Double price) {
        this.amountAvailable = amountAvailable;
        this.name = name;
        this.price = price;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (this.amountAvailable <= 0) {
            errors.add("Amount requested not allowed");
        }
        if (this.name.isEmpty()) {
            errors.add("Product name cannot be empty");
        }
        if (this.price == null || this.price < 0) {
            errors.add("Product price cannot be empty or negative");
        }
        if (this.price == null || this.price < 5) {
            errors.add("Product price should be 5 or more");
        }
        return errors;
    }
}
