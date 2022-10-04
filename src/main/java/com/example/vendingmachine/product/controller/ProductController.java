package com.example.vendingmachine.product.controller;

import com.example.vendingmachine.product.dto.ProductDTO;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.product.service.ProductService;
import com.example.vendingmachine.security.services.UserDetailsServiceImpl;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private ProductService productService;

    private UserService userService;

    private UserDetailsServiceImpl userDetailsService;

    public ProductController(ProductService productService, UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.productService = productService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") UUID id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity list() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity save(@RequestBody ProductDTO productDTO) {
        String currentUser = userDetailsService.getCurrentUser();

        Optional<User> user = userService.findUserByUsername(currentUser);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = new Product(
                productDTO.getName(),
                productDTO.getAmountAvailable(),
                productDTO.getPrice(),
                user.get()
        );
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.delete(product.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
