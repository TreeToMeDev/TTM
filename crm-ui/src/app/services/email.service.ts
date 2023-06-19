import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';

import { EmailRequest } from '../models/email-request';
import { environment } from '../../environments/environment';
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

/* Interface to Gmail, and, in theory, and email provider. Important - the user must have authorized us to 
   use their Gmail account, a process which is managed by the OAuthService, which ultimately writes a record
   into the oauth_token table, which we use to send the email. */

export class EmailService {

  url: string = environment.apiUrl + 'rest/email/';

  constructor(private httpClient: HttpClient) { }

  fetch(contactId: number) {
    // TODO CLASS NAME
    // TODO get from parameter!
    contactId = 3;
    return this.httpClient.get<EmailRequest[]>(this.url + contactId);
  }

  // TODO rename as ADD or whatever, for consistency?
  send(emailRequest: EmailRequest): Observable<any>{
    return this.httpClient.post(this.url, emailRequest);
  }

}
