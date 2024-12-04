package com.pennywise.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pennywise.entity.Expense;
import com.pennywise.service.expense.ExpenseService;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ExpenseService expenseService;

        ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        void setup() {
                objectMapper.registerModule(new JavaTimeModule());
        }

        @Test
        void findExpensesbyUsername() throws Exception {
                List<Expense> expenses = new ArrayList<>();
                Expense expense = new Expense();
                expense.setAmount(1000.0);
                expense.setCategory("Food");
                expense.setModeOfPayment("Credit Card");
                expense.setReason("Dinner");
                expense.setDate(LocalDate.of(2024, 11, 24));
                expenses.add(expense);

                when(expenseService.findExpensesbyUsername("john_doe5", LocalDate.of(2024, 11, 1),
                                LocalDate.of(2024, 11, 30)))
                                .thenReturn(expenses);

                mockMvc.perform(get("/expense/john_doe5")
                                .param("start", "2024-11-01")
                                .param("end", "2024-11-30"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].amount").value(1000.0))
                                .andExpect(jsonPath("$[0].category").value("Food"))
                                .andExpect(jsonPath("$[0].modeOfPayment").value("Credit Card"))
                                .andExpect(jsonPath("$[0].reason").value("Dinner"));
        }

        @Test
        void addExpenseSuccess() throws Exception {
                Expense expense = new Expense();
                expense.setAmount(1000.0);
                expense.setCategory("Food");
                expense.setModeOfPayment("Credit Card");
                expense.setReason("Dinner");
                expense.setDate(LocalDate.of(2024, 11, 24));

                String jsonExpense = objectMapper.writeValueAsString(expense);

                when(expenseService.addExpense(expense)).thenReturn(any(Expense.class));

                mockMvc.perform(post("/expense/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonExpense))
                                .andExpect(status().isCreated())
                                .andExpect(content().string("Expense Added Successfully."));
        }

        @Test
        void addExpenseFailure() throws Exception {
                Expense expense = new Expense();
                expense.setAmount(1000.0);
                expense.setCategory("Food");
                expense.setModeOfPayment("Credit Card");
                expense.setReason("Dinner");
                expense.setDate(LocalDate.of(2024, 11, 24));

                String jsonExpense = objectMapper.writeValueAsString(expense);

                doThrow(new RuntimeException("Unable to add expense")).when(expenseService)
                                .addExpense(any(Expense.class));

                mockMvc.perform(post("/expense/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonExpense))
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().string("Sorry. The expense could not be added."));
        }

        @Test
        void deleteExpenseSuccess() throws Exception {
                doNothing().when(expenseService).deleteExpense(1);

                mockMvc.perform(delete("/expense/delete/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Expense Deleted."));
        }

        @Test
        void deleteExpenseFailure() throws Exception {
                doThrow(new RuntimeException("Unable to delete expense")).when(expenseService).deleteExpense(1);

                mockMvc.perform(delete("/expense/delete/1"))
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().string("Sorry. The expense could not be deleted."));

        }
}
