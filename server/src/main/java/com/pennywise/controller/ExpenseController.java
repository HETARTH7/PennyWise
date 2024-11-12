package com.pennywise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pennywise.entity.Expense;
import com.pennywise.service.expense.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Expense>> findExpensesbyUsername(@PathVariable String username) {
        try {
            List<Expense> expenses = expenseService.findExpensesbyUsername(username);
            expenses.forEach(e -> e.setUser(null));
            return ResponseEntity.ok(expenses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addExpense(@RequestBody Expense expense) {
        try {
            expenseService.addExpense(expense);
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense Added Successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry. The expense could not be added.");
        }
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable int expenseId) {
        try {
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.ok("Expense Deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sorry. The expense could not be added.");
        }
    }
}
