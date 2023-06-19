import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';

import { environment } from '../../environments/environment';
import { Session } from '../models/session';

/*
IDEA: PUT HTTP GETTER IN CONSTRUCTOR! AND MAKE IT WAIT (I GUESS)
FROM THEN ON JUST USE GETTERS 
(SEEMS TO BE FROWNED UPON THOUGH IN ANGULAR FORUMS)
*/

@Injectable({
  providedIn: 'root'
})

// adam@tsh.com BXOtmd319&$*

export class SessionService {

  url: string = environment.apiUrl + 'rest/session';
  
  constructor(private httpClient: HttpClient) { }

  session: Session

  // use a Promise so we can intercept the result of the call and 
  // save it locally; I couldn't think of a way to do that with Observables
  // -- it actually returns a Promise<Session> even though there is no return type

  async getUserInfo() {
    const sessionPromise = lastValueFrom(this.httpClient.get<Session>(this.url));
    await sessionPromise.then(session => {
      this.session = session;
      return session;
    });
  }

  // these calls assume that someone called getUserInfo first, and that it worked.

  getUserName() : String {
    return this.session.name;
  }

  getHasUsersAccess() : Boolean {
    // TODO: this is not backed by server-side checks, need to figure out how to apply
    // these types of restrictions in Spring
    return this.session.accessUsers
  }

  getUserId() : number {
    return this.session.userId
  }

}
