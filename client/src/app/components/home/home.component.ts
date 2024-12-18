import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Category, PaymentMode } from '../../enums';
import { AsyncPipe, NgFor } from '@angular/common';
import { ExpenseService } from '../../services/expense.service';
import { Observable } from 'rxjs';
import { Expense } from '../../interfaces/expense';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, NgFor, AsyncPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  expenses$!: Observable<Expense[]>;
  budget: number = 0.0;
  expenseId: number = 0;
  amount: number = 0;
  reason: string = '';
  currDate: Date = new Date();
  date: string = this.currDate.toISOString().split('T')[0];
  start: string = new Date(
    this.currDate.getFullYear(),
    this.currDate.getMonth(),
    2
  )
    .toISOString()
    .split('T')[0];
  end: string = new Date(
    this.currDate.getFullYear(),
    this.currDate.getMonth() + 1,
    1
  )
    .toISOString()
    .split('T')[0];
  category: Category | null = null;
  modeOfPayment: PaymentMode | null = null;

  categories = Object.values(Category);
  modesOfPayment = Object.values(PaymentMode);

  constructor(
    private expenseService: ExpenseService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.expenses$ = this.expenseService.getExpense(this.start, this.end);
    this.userService.getBudget().subscribe({
      next: (response) => {
        this.budget = response.budget;
      },
      error: (err) => {
        console.error('Failed to fetch budget:', err);
      },
    });
  }

  deleteExpense(expenseId: number): void {
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

  refreshExpenses(): void {
    this.expenses$ = this.expenseService.getExpense(this.start, this.end);
  }

  updateBudget(): void {
    this.userService.updateBudget(this.budget).subscribe({
      next: (data) => {
        console.log(data.message);
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/']);
  }
}
