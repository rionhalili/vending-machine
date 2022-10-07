package com.example.vendingmachine.user.controller;

import com.example.vendingmachine.role.model.Role;
import com.example.vendingmachine.role.model.RoleType;
import com.example.vendingmachine.security.services.UserDetailsServiceImpl;
import com.example.vendingmachine.user.dto.DepositDTO;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DepositController.class)
class DepositControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "buyer")
    @DisplayName("User should deposit coins successfully")
    public void shouldDepositSuccessfully() throws Exception {
        DepositDTO depositDTO = new DepositDTO(100.00);
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );

        when(userDetailsService.getCurrentUser()).thenReturn("buyer");
        when(userService.findUserByUsername("buyer")).thenReturn(Optional.of(user));

        String depositEndpoint = "/api/users/deposit";
        mockMvc.perform(put(depositEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyer")))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "buyer")
    @DisplayName("Should return NOT FOUND when user does not exist on deposit endpoint")
    public void shouldReturnNotFoundWhenUserDoesNotExistOnDepositEndpoint() throws Exception {
        DepositDTO depositDTO = new DepositDTO(100.00);

        String depositEndpoint = "/api/users/deposit";
        mockMvc.perform(put(depositEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyer")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"User not found\"}"));
    }

    @Test
    @WithMockUser(username = "buyer")
    @DisplayName("Should return BAD REQUEST when deposit is bigger than allowed value")
    public void shouldReturnBadRequestWhenDepositIsBiggerThanAllowedValue() throws Exception {
        DepositDTO depositDTO = new DepositDTO(110.00);

        String depositEndpoint = "/api/users/deposit";
        mockMvc.perform(put(depositEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyer")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":[\"Disallowed coin inserted. Coins allowed are: 100.00, 50.00, 20.00, 10.00, 5.00\",\"Maximum coin value exceeded.\"]}"));
    }

    @Test
    @WithMockUser(username = "buyer")
    @DisplayName("Should return BAD REQUEST when deposit is a disallowed value")
    public void shouldReturnBadRequestWhenDepositIsADisallowedValue() throws Exception {
        DepositDTO depositDTO = new DepositDTO(13.00);

        String depositEndpoint = "/api/users/deposit";
        mockMvc.perform(put(depositEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyer")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":[\"Disallowed coin inserted. Coins allowed are: 100.00, 50.00, 20.00, 10.00, 5.00\"]}"));
    }

    @Test
    @WithMockUser(username = "buyer")
    @DisplayName("Should return BAD REQUEST when deposit is bigger than allowed value")
    public void asd() throws Exception {
        DepositDTO depositDTO = new DepositDTO(100.00);
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );

        when(userDetailsService.getCurrentUser()).thenReturn("buyer");
        when(userService.findUserByUsername("buyer")).thenReturn(Optional.of(user));

        String resetEndpoint = "/api/users/reset";
        mockMvc.perform(put(resetEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyer")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "buyer")
    @DisplayName("Should return NOT FOUND when user does not exist on reset endpoint")
    public void shouldReturnNotFoundWhenUserDoesNotExistOnResetEndpoint() throws Exception {
        DepositDTO depositDTO = new DepositDTO(100.00);

        String resetEndpoint = "/api/users/reset";
        mockMvc.perform(put(resetEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositDTO))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyer")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"User not found\"}"));
    }
}