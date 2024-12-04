package com.pennywise.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pennywise.entity.User;
import com.pennywise.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;
    User user;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        user = new User();
        user.setUsername("john_doe4");
        user.setPassword("password123");
        user.setBudget(500.0);
    }

    @Test
    void testSignup_Success() throws Exception {

        when(userService.signup(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully: john_doe4"));
    }

    @Test
    void testSignup_Conflict() throws Exception {
        when(userService.signup(any(User.class))).thenThrow(new RuntimeException("Username already in use"));

        mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Username already in use"));
    }

    @Test
    void testLogin_Success() throws Exception {
        when(userService.login("john_doe4", "password123")).thenReturn(user);

        mockMvc.perform(post("/users/login")
                .param("username", "john_doe4")
                .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string("User logged in: john_doe4"));
    }

    @Test
    void testLogin_Unauthorized() throws Exception {
        when(userService.login("john_doe4", "wrong_password"))
                .thenThrow(new RuntimeException("Incorrect Password"));

        mockMvc.perform(post("/users/login")
                .param("username", "john_doe4")
                .param("password", "wrong_password"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Incorrect Password"));
    }

    @Test
    void testUpdateBudget_Success() throws Exception {
        doNothing().when(userService).updateBudget(1, 1000.0);

        mockMvc.perform(put("/users/update")
                .param("userID", "1")
                .param("budget", "1000.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Budget updated successfully"));
    }
}
