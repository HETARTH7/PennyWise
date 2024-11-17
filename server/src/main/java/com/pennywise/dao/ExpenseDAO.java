package com.pennywise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pennywise.entity.Expense;

public interface ExpenseDAO extends JpaRepository<Expense, Integer> {
    @Query("select e from Expense e where e.user.username=:username")
    List<Expense> findExpensesbyUsername(String username);
}
