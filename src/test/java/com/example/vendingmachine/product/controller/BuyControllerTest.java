package com.example.vendingmachine.product.controller;

import com.example.vendingmachine.product.dto.BuyRequest;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.product.service.ProductServiceImpl;
import com.example.vendingmachine.role.model.Role;
import com.example.vendingmachine.role.model.RoleType;
import com.example.vendingmachine.security.services.UserDetailsServiceImpl;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BuyController.class)
class BuyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private ProductServiceImpl productService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    WebApplicationContext webApplicationContext;

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return BAD REQUEST when there are not enough money in deposit")
    public void shouldReturnBadRequestWhenThereAreNotEnoughMoneyInDeposit() throws Exception {
        BuyRequest buyRequest = new BuyRequest(1);
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );

        Product product = new Product("Coca-cola", 1, 100.00);
        UUID productId = UUID.fromString("725cc71e-a39e-4005-85cb-6dfb45b77646");

        when(userDetailsService.getCurrentUser()).thenReturn("buyer");
        when(userService.findUserByUsername("buyer")).thenReturn(Optional.of(user));
        when(productService.getProduct(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(post("/api/products/725cc71e-a39e-4005-85cb-6dfb45b77646/buy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .param("id", "725cc71e-a39e-4005-85cb-6dfb45b77646")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buyRequest))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"Not enough money in deposit\"}"));
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return OK when user has deposit and request an allowed amount of product")
    public void shouldReturnOKWhenUserHasDepositAndRequestAnAllowedAmountOfProduct() throws Exception {
        BuyRequest buyRequest = new BuyRequest(1);
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        user.setDeposit(200.00);

        Product product = new Product("Coca-cola", 1, 100.00);
        UUID productId = UUID.fromString("725cc71e-a39e-4005-85cb-6dfb45b77646");

        when(userDetailsService.getCurrentUser()).thenReturn("buyer");
        when(userService.findUserByUsername("buyer")).thenReturn(Optional.of(user));
        when(productService.getProduct(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(post("/api/products/725cc71e-a39e-4005-85cb-6dfb45b77646/buy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .param("id", "725cc71e-a39e-4005-85cb-6dfb45b77646")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buyRequest))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return BAD REQUEST when there is an disallowed value for amount of products")
    public void shouldReturnBadRequestWhenThereIsAnDisallowedValueForAmountOfProducts() throws Exception {
        BuyRequest buyRequest = new BuyRequest(-1);
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        user.setDeposit(200.00);

        Product product = new Product("Coca-cola", 1, 100.00);
        UUID productId = UUID.fromString("725cc71e-a39e-4005-85cb-6dfb45b77646");

        when(userDetailsService.getCurrentUser()).thenReturn("buyer");
        when(userService.findUserByUsername("buyer")).thenReturn(Optional.of(user));
        when(productService.getProduct(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(post("/api/products/725cc71e-a39e-4005-85cb-6dfb45b77646/buy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .param("id", "725cc71e-a39e-4005-85cb-6dfb45b77646")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buyRequest))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":[\"Amount requested not allowed\"]}"));
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return NOT FOUND when product is not available")
    public void shouldReturnNotFoundWhenProductIsNotAvailable() throws Exception {
        BuyRequest buyRequest = new BuyRequest(1);
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        user.setDeposit(200.00);

        when(userDetailsService.getCurrentUser()).thenReturn("buyer");
        when(userService.findUserByUsername("buyer")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/products/725cc71e-a39e-4005-85cb-6dfb45b77646/buy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .param("id", "725cc71e-a39e-4005-85cb-6dfb45b77646")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buyRequest))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"Product not found\"}"));
    }
}