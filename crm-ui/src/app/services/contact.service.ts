import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";

import { ActionResponse } from '../models/action-response';
import { Contact } from "../models/contact";
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  url: string = environment.apiUrl + 'rest/contacts';
  
  activeRecord: Contact;

  constructor(private http: HttpClient) { }

  get(accountId: number) : Observable<Contact[]> {
  	return this.http.get<Contact[]>(this.url + '/' + accountId);
  }

  fetchAllContacts() {
    return this.http.get<Contact[]>(this.url);
  }

  fetchContact(id: number) {
    return this.http.get<Contact>(this.url + '/' + id);
  }
  
  fetchContactsByAccount(accountId: number) {
    return this.http.get<Contact[]>(this.url + '/account/' + accountId);
  }

  save(contact: Contact, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(contact)
    } else {
      return this.change(contact, id)
    }
  }

  add(contact: Contact): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(this.url, contact);
  }

  change(contact: Contact, id: number): Observable<ActionResponse> {
    return this.http.patch<ActionResponse>(this.url + "/" + id, contact);
  }

  delete(id: number) : Observable<Boolean> {
  	return this.http.delete<Boolean>(this.url + '/' + id);
  }

  setActiveRecord(contact: Contact) {
    if(contact == null) {
        this.activeRecord = {} as Contact;
    } else {
      this.activeRecord = contact;
    }
  }

}
