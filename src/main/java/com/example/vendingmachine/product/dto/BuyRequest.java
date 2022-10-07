package com.example.vendingmachine.product.dto;

import java.util.ArrayList;
import java.util.List;

public class BuyRequest {
    private int amountOfProducts;

    public BuyRequest() {
    }

    public BuyRequest(int amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public int getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(int amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (this.amountOfProducts <= 0) {
            errors.add("Amount requested not allowed");
        }
        return errors;
    }
}
