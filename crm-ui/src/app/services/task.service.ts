import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable} from "rxjs";

import { ActionResponse } from '../models/action-response';
import { environment } from '../../environments/environment';
import { Task } from "../models/task";

@Injectable({
  providedIn: 'root'
})

export class TaskService {

  url: string = environment.apiUrl + 'rest/tasks';
  
  activeRecord: Task;

  constructor(private http: HttpClient) { }
  
  fetchAll(filter: string) {
    return this.http.get<Task[]>(this.url,
      { params: {
        filter: filter
      }
    });
  }

  fetchAllByAccount(accountId: number, filter: string) {
    return this.http.get<Task[]>(this.url + "/account/" + accountId, 
      { params: {
        filter: filter
      }
    });
  }

  fetchAllByContact(contactId: number, filter: string) {
    return this.http.get<Task[]>(this.url + "/contact/" + contactId, 
      { params: {
        filter: filter
      }
    });
  }

  fetch(id: number) {
    return this.http.get<Task>(this.url + '/' + id);
  }

  save(task: Task, id: number): Observable<ActionResponse> {
    if(isNaN(id) || id == -1) {
      return this.add(task)
    } else {
      return this.change(task, id)
    }
  }

  add(task: Task): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(this.url, task);
  }

  change(task: Task, id: number): Observable<ActionResponse> {
    return this.http.patch<ActionResponse>(this.url + "/" + id, task);
  }

  delete(id: number) : Observable<Boolean> {
  	return this.http.delete<Boolean>(this.url + '/' + id);
  }

}
