package com.pennywise.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pennywise.entity.Expense;

public interface ExpenseDAO extends JpaRepository<Expense, Integer> {
}
