package com.pennywise.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pennywise.entity.User;

@DataJpaTest
public class UserDAOTest {

    @Autowired
    UserDAO userDAO;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setUsername("john_doe1");
        user.setPassword("password123");
        user.setBudget(500.0);
        userDAO.save(user);
    }

    @Test
    public void findByUsername() {
        User user = userDAO.findByUsername("john_doe1").get();
        assertNotNull(user);
        assertEquals(user.getUsername(), "john_doe1");
        assertEquals(user.getPassword(), "password123");
        assertEquals(user.getBudget(), 500);
    }
}
