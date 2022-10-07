package com.example.vendingmachine.product.controller;

import com.example.vendingmachine.product.dto.ProductRequest;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.product.service.ProductService;
import com.example.vendingmachine.security.services.UserDetailsServiceImpl;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    public ProductController(ProductService productService, UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.productService = productService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("id") UUID id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Product not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity list() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<?> save(@RequestBody ProductRequest productRequest) {
        if (!productRequest.validate().isEmpty()) {
            return new ResponseEntity<>(Map.of("message", productRequest.validate()), HttpStatus.BAD_REQUEST);
        }
        String currentUser = userDetailsService.getCurrentUser();
        Optional<User> user = userService.findUserByUsername(currentUser);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Product not owned by user"), HttpStatus.NOT_FOUND);
        }
        Product product = new Product(
                productRequest.getName(),
                productRequest.getAmountAvailable(),
                productRequest.getPrice()
        );
        product.setUserId(user.get());
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity update(@RequestBody ProductRequest productRequest, @PathVariable UUID id) {
        if (!productRequest.validate().isEmpty()) {
            return new ResponseEntity<>(Map.of("message", productRequest.validate()), HttpStatus.BAD_REQUEST);
        }
        String currentUser = userDetailsService.getCurrentUser();

        Optional<User> user = userService.findUserByUsername(currentUser);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Product not owned by user"), HttpStatus.NOT_FOUND);
        }
        Optional<Product> product = productService.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Product not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productService.update(product.get(), productRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable UUID id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Product not found"), HttpStatus.NOT_FOUND);
        }
        productService.delete(product.get());
        return new ResponseEntity<>(Map.of("message", "Product deleted successfully"), HttpStatus.OK);
    }
}
