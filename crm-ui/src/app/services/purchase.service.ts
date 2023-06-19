import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

import { ActionResponse } from '../models/action-response';
import { Purchase } from '../models/purchase';
import { UtilitiesService } from '../services/utilities.service';

@Injectable({
  providedIn: 'root'
})

export class PurchaseService {
	
  constructor(private httpClient: HttpClient, private utilitiesService: UtilitiesService) { }
  
  url: string = environment.apiUrl + 'rest/purchases/';

  activeRecord: Purchase;
  
  setActiveRecord(customerProduct: Purchase) {
	if(customerProduct == null) {
  	  this.activeRecord = {} as Purchase;
	} else {
	  this.activeRecord = customerProduct;
	}
  }
  
  getActiveRecord() {
	return this.activeRecord;
  }

  // TODO NAMES SUCK HERE, more than usual
  get(accountId: number): Observable<Purchase[]> {
	return this.httpClient.get<Purchase[]>(this.url + 'account/' + accountId);
  }
  
  getOne(id: number): Observable<Purchase> {
	return this.httpClient.get<Purchase>(this.url + id);
  }
  
  add(purchase: Purchase): Observable<ActionResponse> {
	let data: Purchase = this.utilitiesService.deepCopy(purchase);
	return this.httpClient.post<ActionResponse>(this.url, data );
  }

  change(purchase: Purchase): Observable<ActionResponse> {
	// TODO why do we do this copy?
	let data: Purchase = this.utilitiesService.deepCopy(purchase);
	return this.httpClient.patch<ActionResponse>(this.url, data);
  }

  delete(id: number): Observable<ActionResponse> {
	return this.httpClient.delete<ActionResponse>(this.url + id);
  }
 
}