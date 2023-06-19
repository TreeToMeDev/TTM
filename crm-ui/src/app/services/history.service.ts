import { environment } from '../../environments/environment';
import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class HistoryService {

  url: string = environment.apiUrl + 'rest/history/';
  
  constructor(private http: HttpClient) { }

  fetchByAccount(accountId: number) {
    return this.http.get<History[]>(this.url + 'account/' + accountId);
  }

  fetchByContact(contactId: number) {
    return this.http.get<History[]>(this.url + 'contact/' + contactId);
  }

  fetchByDeal(dealId: number) {
    return this.http.get<History[]>(this.url + 'deal/' + dealId);
  }

  fetchByTask(taskId: number) {
    return this.http.get<History[]>(this.url + 'task/' + taskId);
  }

}
