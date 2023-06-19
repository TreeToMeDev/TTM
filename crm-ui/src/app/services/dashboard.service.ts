import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AccountCountryTotal } from '../models/account-country-total';
import { AccountTypeTotal } from '../models/account-type-total';
import { Contact } from '../models/contact';
import { Task } from '../models/task';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  url: string = environment.apiUrl + 'rest/dashboard';

  constructor(private http: HttpClient) { }

  fetchOpenTasks(userId: number): Observable<Task[]> {
    return this.http.get<Task[]>(this.url + '/opentasks/' + userId);
  }

  fetchNewLeads(userId: number): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.url + '/newleads/' + userId);
  }

  fetchAccountTypeTotals(userId: number): Observable<AccountTypeTotal[]> {
    return this.http.get<AccountTypeTotal[]>(this.url + '/accounttypetotals/' + userId);  
  }

  fetchAccountCountryTotals(userId: number): Observable<AccountCountryTotal[]> {
    return this.http.get<AccountCountryTotal[]>(this.url + '/accountcountrytotals/' + userId);  
  }
}
