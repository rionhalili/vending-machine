package com.example.vendingmachine.product.dto;

public class ProductDTO {

    private int amountAvailable;
    private String name;
    private Double price;

    public ProductDTO() {
    }

    public ProductDTO(int amountAvailable, String name, Double price) {
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
}
