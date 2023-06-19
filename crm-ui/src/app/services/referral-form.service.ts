import { HttpClient, HttpContext } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

import { ActionResponse } from "../models/action-response";
import { BYPASS_INTERCEPTOR } from '../core/interceptors/http-error-interceptor'
import { environment } from "../../environments/environment";
import { HIDE_DETAILS } from '../core/interceptors/http-error-interceptor'
import { Referral } from "../models/referral";

@Injectable({
    providedIn: 'root'
})
export class ReferralFormService {
    url: string = environment.apiUrl + 'referralform';
  
    activeRecord: Referral;

    constructor(private http: HttpClient) { }

    add(referral: Referral): Observable<ActionResponse> {
        let data: Referral = referral;
        let httpContext = { context: new HttpContext().set(BYPASS_INTERCEPTOR, true ).set(HIDE_DETAILS, true) };
        return this.http.post<ActionResponse>(this.url, data, httpContext );
    }
}