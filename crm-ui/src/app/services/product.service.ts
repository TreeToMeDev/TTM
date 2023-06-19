import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ActionResponse } from '../models/action-response';
import { environment } from '../../environments/environment';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  url: string = environment.apiUrl + 'rest/products';

  constructor(private http: HttpClient) { }

  fetch(id: number): Observable<Product> {
    return this.http.get<Product>(this.url + '/' + id)
  }

  fetchAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.url);
  }

  save(product: Product, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(product)
    } else {
      return this.change(product, id)
    }
  }

  add(product: Product): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(this.url, product);
  }

  change(product: Product, id: number): Observable<ActionResponse> {
    return this.http.patch<ActionResponse>(this.url + "/" + id, product);
  }

  delete(id: number) : Observable<Boolean> {
  	return this.http.delete<Boolean>(this.url + '/' + id);
  }

}
