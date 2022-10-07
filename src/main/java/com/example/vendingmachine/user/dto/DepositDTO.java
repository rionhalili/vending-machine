package com.example.vendingmachine.user.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        List<Double> AVAILABLE_COINS = List.of(100.00, 50.00, 20.00, 10.00, 5.00);
        List<String> errors = new ArrayList<>();
        if (!AVAILABLE_COINS.contains(this.depositPrice)) {
            errors.add("Disallowed coin inserted. Coins allowed are: 100.00, 50.00, 20.00, 10.00, 5.00");
        }
        if (this.depositPrice <= 0.0) {
            errors.add("Price requested not allowed");
        }
        if(this.depositPrice > 100) {
            errors.add("Maximum coin value exceeded.");
        }
        return errors;
    }
}
