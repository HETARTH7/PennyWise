package com.pennywise.service.user;

import com.pennywise.entity.User;

public interface UserService {
    User signup(User user);

    User login(String username, String password);

    void updateBudget(int userID, Double budget);
}