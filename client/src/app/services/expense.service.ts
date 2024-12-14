import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Expense } from '../interfaces/expense';

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private baseURL = 'http://localhost:8080/expense';

  constructor(private http: HttpClient) {}

  public getExpense(start: string, end: string): Observable<Expense[]> {
    const username = localStorage.getItem('user');
    let params = new HttpParams().set('username', username || '');
    if (start) {
      params = params.set('start', start);
    }
    if (end) {
      params = params.set('end', end);
    }
    return this.http.get<Expense[]>(`${this.baseURL}/${username}`, { params });
  }

  public addExpense(expense: Expense): Observable<any> {
    return this.http.post(`${this.baseURL}/add`, {
      user: { userID: localStorage.getItem('userID') },
      ...expense,
    });
  }
}
