package com.example.vendingmachine.product.dto;

import java.util.UUID;

public class BuyDTO {
    private UUID productId;
    private int amountOfProducts;

    public BuyDTO() {
    }

    public BuyDTO(UUID productId, int amountOfProducts) {
        this.productId = productId;
        this.amountOfProducts = amountOfProducts;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(int amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }
}
