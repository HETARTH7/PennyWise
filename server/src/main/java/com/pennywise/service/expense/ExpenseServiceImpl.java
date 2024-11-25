package com.pennywise.service.expense;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennywise.dao.ExpenseDAO;
import com.pennywise.entity.Expense;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseDAO expenseDAO;

    @Override
    public Expense addExpense(Expense expense) {
        return expenseDAO.save(expense);
    }

    @Override
    public void deleteExpense(int expenseId) {
        expenseDAO.deleteById(expenseId);
    }

    @Override
    public List<Expense> findExpensesbyUsername(String username, LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            LocalDate now = LocalDate.now();
            start = now.withDayOfMonth(1);
            end = now.withDayOfMonth(now.lengthOfMonth());
        }
        return expenseDAO.findExpensesbyUsername(username, start, end);
    }

}
