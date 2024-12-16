import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Category, PaymentMode } from '../../enums';
import { AsyncPipe, NgFor } from '@angular/common';
import { ExpenseService } from '../../services/expense.service';
import { Observable } from 'rxjs';
import { Expense } from '../../interfaces/expense';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, NgFor, AsyncPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  expenses$!: Observable<Expense[]>;
  expenseId: number = 0;
  amount: number = 0;
  reason: string = '';
  date: string = new Date().toISOString().split('T')[0];
  category: Category | null = null;
  modeOfPayment: PaymentMode | null = null;

  categories = Object.values(Category);
  modesOfPayment = Object.values(PaymentMode);

  constructor(private expenseService: ExpenseService) {}

  ngOnInit(): void {
    this.expenses$ = this.expenseService.getExpense('', '');
  }

  onClick(expenseId: number): void {
    this.expenseService.deleteExpense(expenseId).subscribe({
      next: (data) => {
        console.log(data.message);
        this.refreshExpenses();
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  onSubmit(): void {
    const expense = {
      expenseId: this.expenseId,
      amount: this.amount,
      reason: this.reason,
      date: this.date,
      category: this.category,
      modeOfPayment: this.modeOfPayment,
    };

    this.expenseService.addExpense(expense).subscribe({
      next: (data) => {
        console.log(data.message);
        this.refreshExpenses();
      },
      error: (err) => {
        console.error(err);
      },
    });
    this.amount = 0;
    this.reason = '';
    this.date = new Date().toISOString().split('T')[0];
    this.category = null;
    this.modeOfPayment = null;
  }

  private refreshExpenses(): void {
    this.expenses$ = this.expenseService.getExpense('', '');
  }
}
