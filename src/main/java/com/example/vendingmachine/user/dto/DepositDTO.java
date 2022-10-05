package com.example.vendingmachine.user.dto;

public class DepositDTO {
    private Double depositPrice;

    public DepositDTO() {
    }

    public DepositDTO(Double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public Double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(Double depositPrice) {
        this.depositPrice = depositPrice;
    }
}
