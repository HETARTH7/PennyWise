package com.pennywise.service.expense;

import java.util.List;

import com.pennywise.entity.Expense;

public interface ExpenseService {
    Expense addExpense(Expense expense);

    void deleteExpense(int expenseId);

    List<Expense> findExpensesbyUsername(String username);
}