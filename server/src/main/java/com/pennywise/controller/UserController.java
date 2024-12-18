package com.pennywise.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pennywise.entity.User;
import com.pennywise.service.user.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        try {
            User newUser = userService.signup(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User created successfully");
            response.put("userID", Integer.toString(newUser.getUserID()));
            response.put("username", newUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User userLogin) {
        try {
            User user = userService.login(userLogin.getUsername(), userLogin.getPassword());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login Sucessfull");
            response.put("userID", Integer.toString(user.getUserID()));
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        }
    }

    @GetMapping("/budget/{userID}")
    public ResponseEntity<Map<String, String>> getBudget(@PathVariable int userID) {
        try {
            Double budget = userService.getBudget(userID);
            Map<String, String> response = new HashMap<>();
            response.put("budget", budget.toString());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateBudget(@RequestParam int userID, @RequestParam Double budget) {
        try {
            userService.updateBudget(userID, budget);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Budget updated successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
        }
    }
}
