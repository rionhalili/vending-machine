package com.example.vendingmachine.user.dto;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (this.depositPrice <= 0.0) {
            errors.add("Price requested not allowed");
        }
        if(this.depositPrice > 100) {
            errors.add("Maximum coin value exceeded.");
        }
        return errors;
    }
}
