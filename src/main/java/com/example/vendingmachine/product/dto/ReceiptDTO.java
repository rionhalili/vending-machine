package com.example.vendingmachine.product.dto;

import java.util.List;

public class ReceiptDTO {
    private Double total;
    private ProductRequest productRequest;
    private List<Integer> change;

    public ReceiptDTO() {
    }

    public ReceiptDTO(Double total, ProductRequest productRequest, List<Integer> change) {
        this.total = total;
        this.productRequest = productRequest;
        this.change = change;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ProductRequest getProductDTO() {
        return productRequest;
    }

    public void setProductDTO(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }

    public List<Integer> getChange() {
        return change;
    }

    public void setChange(List<Integer> change) {
        this.change = change;
    }
}
