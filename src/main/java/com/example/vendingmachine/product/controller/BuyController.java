package com.example.vendingmachine.product.controller;

import com.example.vendingmachine.product.dto.BuyDTO;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.product.service.ProductService;
import com.example.vendingmachine.security.services.UserDetailsServiceImpl;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class BuyController {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final ProductService productService;

    public BuyController(UserDetailsServiceImpl userDetailsService, UserService userService, ProductService productService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping(value = "/{id}/buy", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public ResponseEntity<?> buy(@PathVariable UUID id, @RequestBody BuyDTO buyDTO) {
        if (buyDTO.validate().size() != 0) {
            return new ResponseEntity<>(Map.of("message", buyDTO.validate()), HttpStatus.BAD_REQUEST);
        }
        String currentUser = userDetailsService.getCurrentUser();
        Optional<User> user = userService.findUserByUsername(currentUser);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Product not owned by user"), HttpStatus.NOT_FOUND);
        }
        Optional<Product> product = productService.getProduct(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Product not found"), HttpStatus.NOT_FOUND);
        }
        if (product.get().getAmountAvailable() < buyDTO.getAmountOfProducts()) {
            return new ResponseEntity<>(Map.of("message", "Amount of products not available"), HttpStatus.BAD_REQUEST);
        }

        if (product.get().getPrice() > user.get().getDeposit()) {
            return new ResponseEntity<>(Map.of("message", "Not enough money in deposit"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productService.buy(user.get(), product.get(), buyDTO), HttpStatus.OK);
    }
}
