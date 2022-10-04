package com.example.vendingmachine.product.service;

import com.example.vendingmachine.product.dto.ProductDTO;
import com.example.vendingmachine.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Optional<Product> getProduct(UUID id);

    List<Product> getProducts();

    void delete(Product product);

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Product update(Product product, ProductDTO productDTO);
}
