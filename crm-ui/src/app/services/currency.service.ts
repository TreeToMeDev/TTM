import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Currency } from '../models/currency';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  url: string = environment.apiUrl + 'rest/currencies';
  
  constructor(private http: HttpClient) { }

  fetchAllCurrencies() : Observable<Currency[]> {
    return this.http.get<Currency[]>(this.url);
  }

  //  Sort currencies by description
  sortByDescription(currencies: Currency[]) {
    var currencyList: Currency[] = currencies.sort((n1, n2) => {
      if (n1.description > n2.description) {
        return 1;
      }
      if (n1.description < n2.description) {
        return -1;
      }
      return 0;
    })
  }
}
