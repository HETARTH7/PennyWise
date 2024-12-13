import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Category, PaymentMode } from '../../enums';
import { NgFor } from '@angular/common';
import { ExpenseService } from '../../services/expense.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, NgFor],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  constructor(private expenseService: ExpenseService) {}

  amount: number = 0;
  reason: string = '';
  date: string = new Date().toISOString().split('T')[0];
  category: Category | null = null;
  modeOfPayment: PaymentMode | null = null;

  categories = Object.values(Category);
  madesOfPayment = Object.values(PaymentMode);

  onSubmit(): void {
    this.expenseService
      .addExpense(
        this.amount,
        this.reason,
        this.date,
        this.category,
        this.modeOfPayment
      )
      .subscribe({
        next: (data) => {
          console.log(data.message);
        },
        error: (err) => {
          console.error(err);
        },
      });
    this.amount = 0;
    this.reason = '';
    this.date = '';
    this.modeOfPayment = null;
    this.category = null;
  }
}
