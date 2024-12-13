import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  baseURL = 'http://localhost:8080/expense';
  constructor(private http: HttpClient) {}

  addExpense(
    amount: number,
    reason: string,
    date: string,
    category: string | null,
    modeOfPayment: string | null
  ): Observable<any> {
    return this.http.post(`${this.baseURL}/add`, {
      user: { userID: localStorage.getItem('userID') },
      amount,
      reason,
      date,
      category,
      modeOfPayment,
    });
  }
}
