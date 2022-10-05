package com.example.vendingmachine.product.service;

import com.example.vendingmachine.product.dto.BuyDTO;
import com.example.vendingmachine.product.dto.ProductDTO;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getProduct(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product save(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Product update(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setAmountAvailable(productDTO.getAmountAvailable());
        return productRepository.save(product);
    }

    @Override
    public Product buy(Product product, BuyDTO buyDTO) {
        int finalAmountOfProducts = product.getAmountAvailable() - buyDTO.getAmountOfProducts();
        product.setAmountAvailable(finalAmountOfProducts);
        return productRepository.save(product);
    }
}
