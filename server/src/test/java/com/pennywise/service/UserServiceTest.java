package com.pennywise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pennywise.dao.UserDAO;
import com.pennywise.entity.User;
import com.pennywise.service.user.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void validSignup() {
        User user = new User();
        user.setUsername("john_doe3");
        user.setPassword("password123");
        user.setBudget(500.0);

        when(userDAO.findByUsername("john_doe3")).thenReturn(Optional.empty());
        when(userDAO.save(user)).thenReturn(user);

        User newUser = userService.signup(user);
        assertNotNull(newUser);
        assertEquals(newUser.getUsername(), "john_doe3");
    }

    @Test
    public void invalidSignup() {
        User user = new User();
        user.setUsername("john_doe3");
        user.setPassword("password123");
        user.setBudget(500.0);

        when(userDAO.findByUsername("john_doe3")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.signup(user));

        assertEquals("Username already in use", exception.getMessage());
        verify(userDAO, never()).save(user);
    }

    @Test
    public void successfullLogin() {
        User user = new User();
        user.setUsername("john_doe3");
        user.setPassword("password123");

        when(userDAO.findByUsername("john_doe3")).thenReturn(Optional.of(user));

        User loginUser = userService.login(user.getUsername(), user.getPassword());
        assertNotNull(loginUser);
        assertEquals(user.getUsername(), loginUser.getUsername());
    }

    @Test
    public void loginWithIncorrectUsername() {
        User user = new User();
        user.setUsername("john_doe3");
        user.setPassword("password123");

        when(userDAO.findByUsername("john_doe3")).thenReturn(Optional.empty());

        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.login(user.getUsername(), user.getPassword()));
        assertEquals(e.getMessage(), "No such username exists");
    }

    @Test
    public void loginWithIncorrectPassword() {
        User user = new User();
        user.setUsername("john_doe3");
        user.setPassword("password123");

        when(userDAO.findByUsername("john_doe3")).thenReturn(Optional.of(user));

        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.login(user.getUsername(), "password12"));
        assertEquals(e.getMessage(), "Incorrect Password");
    }

    @Test
    public void updateBudgetSuccess() {
        User user = new User();
        user.setUsername("john_doe3");
        user.setPassword("password123");
        user.setBudget(500.0);

        when(userDAO.findById(1)).thenReturn(Optional.of(user));
        when(userDAO.save(user)).thenReturn(user);

        userService.updateBudget(1, 1500.00);

        assertEquals(1500.00, user.getBudget());
        verify(userDAO, times(1)).save(user);
    }

    @Test
    void updateBudgetFailure() {
        User user = new User();
        user.setUsername("john_doe3");
        user.setPassword("password123");
        user.setBudget(500.0);

        when(userDAO.findById(1)).thenReturn(Optional.of(user));

        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.updateBudget(1, null));
        assertEquals(e.getMessage(), "Cannot update budget");
    }
}
