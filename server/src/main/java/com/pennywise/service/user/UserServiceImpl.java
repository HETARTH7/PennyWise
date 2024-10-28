package com.pennywise.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennywise.dao.UserDAO;
import com.pennywise.entity.User;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User signup(User user) {
        Optional<User> existingUser = userDAO.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already in use");
        }
        return userDAO.save(user);
    }

    @Override
    public User login(String username, String password) {
        Optional<User> user = userDAO.findByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("No such username exists");
        }
        if (!user.get().getPassword().equals(password)) {
            throw new RuntimeException("Incorrect Password");
        }
        return user.get();
    }

    @Override
    public void updateBudget(int userID, Double budget) {
        Optional<User> user = userDAO.findById(userID);
        if (budget == null) {
            throw new RuntimeException("Cannot update budget");
        }
        user.get().setBudget(budget);
        userDAO.save(user.get());
    }

}
