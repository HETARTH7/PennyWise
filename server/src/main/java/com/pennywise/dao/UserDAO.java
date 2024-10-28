package com.pennywise.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pennywise.entity.User;
import java.util.*;

public interface UserDAO extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
