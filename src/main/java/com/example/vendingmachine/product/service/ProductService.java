package com.example.vendingmachine.product.service;

import com.example.vendingmachine.product.dto.BuyRequest;
import com.example.vendingmachine.product.dto.ProductRequest;
import com.example.vendingmachine.product.dto.ReceiptDTO;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Optional<Product> getProduct(UUID id);

    List<Product> getProducts();

    void delete(Product product);

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Product update(Product product, ProductRequest productRequest);

    ReceiptDTO buy(User user, Product product, BuyRequest buyRequest);
}
