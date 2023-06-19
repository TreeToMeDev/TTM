import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { EMPTY, Observable, of } from "rxjs";

import { ActionResponse } from '../models/action-response';
import { DealItem } from "../models/deal-item";
import { environment } from '../../environments/environment';
import { Action } from "rxjs/internal/scheduler/Action";

@Injectable({
  providedIn: 'root'
})

export class DealItemService {

  url: string = environment.apiUrl + 'rest/deal_items';
  
  activeRecord: DealItem;
  temporaryId = -2; // must start at -2 to indicate Edit of Unsaved item

  /* Handle special case of DealItems being added prior to Deal being saved in the DB. Save
     the DealItems locally until we have a 'real' Deal to save them into the DB under.
     In other cases, all DealItems come directly from the DB. */
  localCache: DealItem[] = [];

  constructor(private http: HttpClient) { }

  fetch(id: number) : Observable<DealItem> {
    if(id < 0) {
      let dealItem = this.localCache.find(element => element.id == id);
      if(dealItem) {
        return of(dealItem);
      } else {
        return EMPTY;
      }
    } else {
      return this.http.get<DealItem>(this.url + '/' + id);
    }
  }

  // will only work if NG services are singletons! 
  saveNewDealDetails(dealId: number) {
    for(let i=0;i<this.localCache.length;i++) {
      let dealItem = this.localCache[i];
      dealItem.dealId = dealId;
      this.add(dealItem).subscribe();
    }
    this.localCache = [];
  }
  
  fetchByDeal(dealId: number) : Observable<DealItem[]> {
    if(dealId == -1) {
      return of(this.localCache.map(x => Object.assign({}, x)));
    } else {
      return this.http.get<DealItem[]>(this.url + '/deal/' + dealId);
    }
  }

  save(dealItem: DealItem, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(dealItem);
    } else {
      return this.change(dealItem, id);
    }
  }

  // TODO reset cache between Deals !!
  add(dealItem: DealItem): Observable<ActionResponse> {
    if(dealItem.dealId == -1) {
      // need to assign temporary ID to make changes to unsaved line items work properly
      dealItem.id = this.temporaryId--;
      this.localCache.push(dealItem);
      return of(new Object as ActionResponse);
    } else {
      return this.http.post<ActionResponse>(this.url, dealItem);
    }
  }

  change(dealItem: DealItem, id: number): Observable<ActionResponse> {
    if(dealItem.dealId == -1) {
      let i = this.localCache.findIndex(element => element.id == id);
      if(i >= 0) {
        this.localCache[i] = dealItem;
        dealItem.id = id;
      } else {
        // TODO better display
        alert('cant find changed item in local cache');
      }
      return of(new Object as ActionResponse);
    } else {
      return this.http.patch<ActionResponse>(this.url + "/" + id, dealItem);
    }
  }

  delete(id: number) : Observable<Boolean> {
    if(id <= -2) {
      let i = this.localCache.findIndex(element => element.id == id);
      if(i >= 0) {
        this.localCache.splice(i,1);
      } else {
        alert('cant find deleted item in local cache');
      }
      return of(true);
    } else {
  	  return this.http.delete<Boolean>(this.url + '/' + id);
    }
  }

}