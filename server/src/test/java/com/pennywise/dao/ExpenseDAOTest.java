package com.pennywise.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pennywise.entity.Expense;
import com.pennywise.entity.User;

@DataJpaTest
public class ExpenseDAOTest {
    @Autowired
    ExpenseDAO expenseDAO;

    @Autowired
    UserDAO userDAO;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setUsername("john_doe2");
        user.setPassword("password123");
        user.setBudget(500.0);
        User saved = userDAO.save(user);

        Expense expense = new Expense();
        user.setUserID(saved.getUserID());
        expense.setAmount(1000.00);
        expense.setCategory("Food Order");
        expense.setModeOfPayment("Credit Card");
        expense.setReason("KFC Lunch");
        expense.setUser(user);
        expense.setDate(LocalDate.parse("2024-11-25"));
        expenseDAO.save(expense);
    }

    @Test
    void findExpensesbyUsername() {
        List<Expense> expenses = expenseDAO.findExpensesbyUsername("john_doe2", LocalDate.parse("2024-11-01"),
                LocalDate.parse("2024-11-30"));
        assertNotNull(expenses);

        Expense expense = expenses.get(0);
        assertEquals(expense.getAmount(), 1000.00);
        assertEquals(expense.getCategory(), "Food Order");
        assertEquals(expense.getModeOfPayment(), "Credit Card");
        assertEquals(expense.getReason(), "KFC Lunch");
    }
}
