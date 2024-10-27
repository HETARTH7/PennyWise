package com.pennywise.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennywise.dao.UserDAO;
import com.pennywise.entity.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User signup(User user) {
        return userDAO.save(user);
    }

    @Override
    public User login(String username, String password) {

    }

    @Override
    public void updateBudget(int userID, Double budget) {

    }

}
