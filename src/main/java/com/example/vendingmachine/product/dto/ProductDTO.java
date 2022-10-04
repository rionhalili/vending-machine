package com.example.vendingmachine.product.dto;

public class ProductDTO {

    private Double amountAvailable;
    private String name;
    private Double price;

    public ProductDTO() {
    }

    public ProductDTO(Double amountAvailable, String name, Double price) {
        this.amountAvailable = amountAvailable;
        this.name = name;
        this.price = price;
    }

    public Double getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(Double amountAvailable) {
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
