package com.example.vendingmachine.product.dto;

public class ReceiptDTO {
    private Double total;
    private ProductDTO productDTO;
    private Double change;

    public ReceiptDTO() {
    }

    public ReceiptDTO(Double total, ProductDTO productDTO, Double change) {
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

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }
}
