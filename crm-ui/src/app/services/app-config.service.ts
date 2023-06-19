import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AppConfig } from '../models/app-config';

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {
  
  url: string = environment.apiUrl + 'rest/appconfig';
  
  constructor(private http: HttpClient) { }

  fetch(category: string, option: string): Observable<AppConfig> {
    return this.http.get<AppConfig>(this.url + '/' + category + '/' + option);
  }

  fetchAllByCategory(category: string) : Observable<AppConfig[]> {
    return this.http.get<AppConfig[]>(this.url + '/' + category);
  }
}
