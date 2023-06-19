import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable} from "rxjs";

import { ActionResponse } from '../models/action-response';
import { Deal } from "../models/deal";
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class DealService {

  url: string = environment.apiUrl + 'rest/deals';
  
  activeRecord: Deal;

  constructor(private http: HttpClient) { }
  
  fetchAll(filter: string) {
    return this.http.get<Deal[]>(this.url,
      { params: {
        filter: filter
      }
    });
  }

  fetchAllByAccount(accountId: number, filter: string) {
    return this.http.get<Deal[]>(this.url + "/account/" + accountId, 
      { params: {
        filter: filter
      }
    });
  }

  fetchAllByContact(contactId: number, filter: string) {
    return this.http.get<Deal[]>(this.url + "/contact/" + contactId, 
      { params: {
        filter: filter
      }
    });
  }

  fetch(id: number) {
    return this.http.get<Deal>(this.url + '/' + id);
  }

  save(deal: Deal, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(deal)
    } else {
      return this.change(deal, id)
    }
  }

  add(deal: Deal): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(this.url, deal);
  }

  change(deal: Deal, id: number): Observable<ActionResponse> {
    return this.http.patch<ActionResponse>(this.url + "/" + id, deal);
  }

  delete(id: number) : Observable<Boolean> {
  	return this.http.delete<Boolean>(this.url + '/' + id);
  }

}