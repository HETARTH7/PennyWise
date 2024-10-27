package com.pennywise.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pennywise.entity.User;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByUsername(int userID);
}
