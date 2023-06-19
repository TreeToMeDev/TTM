import { Account } from '../models/account'
import { ActionResponse } from '../models/action-response';
import { environment } from '../../environments/environment';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AccountsService {

  url: string = environment.apiUrl + 'rest/accounts';

  constructor(private http: HttpClient) { }

  fetch(id: number): Observable<Account> {
    return this.http.get<Account>(this.url + '/' + id);
  }

  fetchAll(): Observable<Account[]> {
    return this.http.get<Account[]>(this.url);
  }

  save(account: Account, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(account)
    } else {
      return this.change(account, id)
    }
  }

  add(account: Account): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(this.url, account);
  }

  change(account: Account, id: number) {
    return this.http.patch<ActionResponse>(this.url + "/" + id, account);
  }

  delete(id: number) : Observable<Boolean> {
  	return this.http.delete<Boolean>(this.url + '/' + id);
  }

}