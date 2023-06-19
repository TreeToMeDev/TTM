import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ContactTypeListItem } from '../models/contact-type-list-item';

@Injectable({
  providedIn: 'root'
})
export class ContactTypeListService {

  url: string = environment.apiUrl + 'rest/contacttypelist';
  
  constructor(private http: HttpClient) { }

  fetchAll() : Observable<ContactTypeListItem[]> {
    return this.http.get<ContactTypeListItem[]>(this.url);
  }
}
