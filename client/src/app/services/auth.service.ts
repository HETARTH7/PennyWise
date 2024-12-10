import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  baseURL = 'http://localhost:8080/users';
  constructor(private http: HttpClient) {}

  createLocalStorageToken(username: string) {
    localStorage.setItem('user', username);
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseURL}/login`, { username, password });
  }

  signup(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseURL}/signup`, { username, password });
  }
}
