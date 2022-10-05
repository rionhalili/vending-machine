package com.example.vendingmachine.product.dto;

import java.util.List;

public class ReceiptDTO {
    private Double total;
    private ProductDTO productDTO;
    private List<Integer> change;

    public ReceiptDTO() {
    }

    public ReceiptDTO(Double total, ProductDTO productDTO, List<Integer> change) {
        this.total = total;
        this.productDTO = productDTO;
        this.change = change;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public List<Integer> getChange() {
        return change;
    }

    public void setChange(List<Integer> change) {
        this.change = change;
    }
}
