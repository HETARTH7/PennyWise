package com.pennywise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pennywise.dao.ExpenseDAO;
import com.pennywise.entity.Expense;
import com.pennywise.entity.User;
import com.pennywise.service.expense.ExpenseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private ExpenseDAO expenseDAO;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense expense;

    @BeforeEach
    public void setup() {
        User mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setUsername("john_doe");

        expense = new Expense();
        expense.setExpenseId(1);
        expense.setUser(mockUser);
        expense.setAmount(100.0);
        expense.setCategory("Food");
        expense.setModeOfPayment("Credit Card");
        expense.setReason("Lunch");
        expense.setDate(LocalDate.of(2024, 11, 25));
    }

    @Test
    public void addExpense() {
        when(expenseDAO.save(expense)).thenReturn(expense);

        Expense newExpense = expenseService.addExpense(expense);

        assertNotNull(newExpense);
        assertEquals(100.0, newExpense.getAmount());
        verify(expenseDAO, times(1)).save(expense);
    }

    @Test
    public void findExpensesbyUsername() {
        when(expenseDAO.findExpensesbyUsername("john_doe", LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30))).thenReturn(Arrays.asList(expense));

        List<Expense> expenses = expenseService.findExpensesbyUsername("john_doe", LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30));

        assertNotNull(expenses);
        assertEquals(expenses.get(0).getAmount(), 100.0);
    }
}
