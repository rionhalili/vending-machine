package com.example.vendingmachine.user.controller;

import com.example.vendingmachine.role.model.Role;
import com.example.vendingmachine.role.model.RoleType;
import com.example.vendingmachine.user.dto.UserRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return requested user")
    public void shouldReturnRequestedUser() throws Exception {
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        String GET_USER_ENDPOINT = "/api/users/" + user.getId();

        when(userService.getUser(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(get(GET_USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return NOT FOUND when user does not exist")
    public void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        String GET_USER_ENDPOINT = "/api/users/" + user.getId();

        mockMvc.perform(get(GET_USER_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"User not found\"}"));
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should update user successfully")
    public void shouldUpdateUserSuccessfully() throws Exception {
        UserRequest userRequest = new UserRequest("updatedUser", "password");
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        String UPDATE_ENDPOINT = "/api/users/" + user.getId();

        when(userService.getUser(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(put(UPDATE_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return NOT FOUND when requested to update user")
    public void shouldReturnNotFoundWhenRequestedToUpdateUser() throws Exception {
        UserRequest userRequest = new UserRequest("updatedUser", "password");
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        String UPDATE_ENDPOINT = "/api/users/" + user.getId();


        mockMvc.perform(put(UPDATE_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"User not found\"}"));
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should delete user successfully")
    public void shouldDeleteUserSuccessfully() throws Exception {
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        String DELETE_ENDPOINT = "/api/users/" + user.getId();

        when(userService.getUser(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(delete(DELETE_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"User deleted successfully\"}"));
    }

    @Test
    @WithMockUser(value = "buyerUser")
    @DisplayName("Should return NOT FOUND when requested to delete user")
    public void shouldReturnNotFoundWhenRequestedToDeleteUser() throws Exception {
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User(
                "buyer",
                "password",
                role
        );
        String DELETE_ENDPOINT = "/api/users/" + user.getId();

        mockMvc.perform(delete(DELETE_ENDPOINT)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("buyerUser")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"User not found\"}"));
    }
}