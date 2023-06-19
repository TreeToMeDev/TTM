import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";

import { ActionResponse } from '../models/action-response';
import { environment } from '../../environments/environment';
import { User } from "../models/user";

@Injectable({
  providedIn: 'root'
})

export class UserService {

  url: string = environment.apiUrl + 'rest/users';
  
  constructor(private http: HttpClient) { }

  fetchAllUsers() : Observable<User[]> {
    return this.http.get<User[]>(this.url);
  }

  fetchUser(id: number) {
    return this.http.get<User>(this.url + '/' + id);
  }

  save(user: User, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(user)
    } else {
      return this.change(user, id)
    }
  }

  add(user: User): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(this.url, user);
  }

  change(user: User, id: number): Observable<ActionResponse> {
    return this.http.patch<ActionResponse>(this.url + "/" + id, user);
  }

  delete(id: number) : Observable<Boolean> {
  	return this.http.delete<Boolean>(this.url + '/' + id);
  }

}
