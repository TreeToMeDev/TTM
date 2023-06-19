import { Note } from '../models/note'
import { environment } from '../../environments/environment';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class NotesService {

  url: string = environment.apiUrl + 'rest/notes/';

  constructor(private httpClient: HttpClient) { }

  add(note: Note) {
    return this.httpClient.request<void>('POST', this.url, { body: note });
  }

  // change(note: Note) {
  //   return this.httpClient.request<void>('PATCH', this.url, { body: note });
  // }

  fetch(accountId: number, contactId: number, referralId: number): Observable<Note[]> {
    // FFS is it 0 or -1 ????
    if(contactId && contactId != -1 && contactId != 0) {
      return this.httpClient.get<Note[]>(this.url + 'contact/' + contactId);
    } else if (referralId && referralId != -1 && referralId != 0) {
      return this.httpClient.get<Note[]>(this.url + 'referral/' + referralId);
    } else {
      return this.httpClient.get<Note[]>(this.url + 'account/' + accountId);
    }
  }

  fetchOne(id: number): Observable<Note> {
    return this.httpClient.get<Note>(this.url + id);
  }

  // delete(id: number) : Observable<Boolean> {
  //   return this.httpClient.delete<Boolean>(this.url + id);
  // }

}