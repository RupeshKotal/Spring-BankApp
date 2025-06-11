import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Account {
  id: number;
  accountNumber: string;
  type: string;
  balance: number;
  createdAt: string;
  updatedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = `${environment.apiUrl}/api/accounts`;

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.apiUrl);
  }

  getAccount(id: number): Observable<Account> {
    return this.http.get<Account>(`${this.apiUrl}/${id}`);
  }

  createAccount(accountData: Partial<Account>): Observable<Account> {
    return this.http.post<Account>(this.apiUrl, accountData);
  }

  transfer(accountId: number, transferData: { toAccountNumber: string; amount: number }): Observable<any> {
    return this.http.post(`${this.apiUrl}/${accountId}/transfer`, transferData);
  }
} 