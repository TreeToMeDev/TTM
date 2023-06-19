import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { OAuthReply } from "../models/oauth-reply";

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})

/* Deal with OAuth authorization token required for Gmail API access. This token could
   also be used for other Google services as well as services provided by other vendors
   that use OAuth. But we're only using it for Gmail. */

export class OAuthService {

  url: string = environment.apiUrl + 'rest/oauth';

  constructor(private httpClient: HttpClient) { }

  /* Back end will look up in a table to see if we already have an OAuth token for this guy (we only
     need to get it once). If we don't it returns a URL we can use to redirect to to get authorization. 
     We need to know where the user came from (sourceUrl) so we know where to redirect back to after
     the authorization is complete (Google handles this). */

  getIsAuthorized(sourceUrl: string): Observable<OAuthReply> {
    // for some reason this can't be a path variable, has to be a URL parameter.
    // but it seems it doesn't need to be escaped, so that's nice.
    let wholeUrl = this.url + '?redirect=' + sourceUrl;
    return this.httpClient.get<OAuthReply>(wholeUrl);
  }

  /* Once we get the access code, save it in a back-end table so we don't have to authorize every time. */

  saveAccessCode(urlWithAccessCode: string, sourceUrl) {
    let sourceUrlWithoutParam = sourceUrl.split('?')[0];
    let oAuthRequest = {
      redirectUrl: urlWithAccessCode,
      sourceUrl: sourceUrlWithoutParam
    }
    return this.httpClient.post(this.url, oAuthRequest);
  }

}
