import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseURL = 'http://localhost:8080/users';
  private userID = localStorage.getItem('userID');
  constructor(private http: HttpClient) {}

  getBudget(): Observable<User> {
    return this.http.get<User>(`${this.baseURL}/budget/${this.userID}`);
  }

  updateBudget(newBudget: number): Observable<any> {
    return this.http.put(
      `${this.baseURL}/update?userID=${this.userID}&budget=${newBudget}`,
      {}
    );
  }
}
