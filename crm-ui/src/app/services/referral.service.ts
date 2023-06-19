import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

import { ActionResponse } from "../models/action-response";
import { environment } from "../../environments/environment";
import { Referral } from "../models/referral";

@Injectable({
  providedIn: 'root'
})
export class ReferralService {

    url: string = environment.apiUrl + 'rest/referrals';
  
    activeRecord: Referral;
  
    constructor(private http: HttpClient) { }
    
    fetchAll(filter: string) {
      let params = { filter: filter };
      return this.http.get<Referral[]>(this.url, {
        params: params,
      });
    }
  
    fetch(id: number) : Observable<Referral>{
      return this.http.get<Referral>(this.url + '/' + id);
    }

    save(referral: Referral, id: number): Observable<ActionResponse> {
      if(isNaN(id) || id == -1) {
        return this.add(referral)
      } else {
        return this.change(referral, id)
      }
    }
  
    add(referral: Referral): Observable<ActionResponse> {
      let data: Referral = referral;
      return this.http.post<ActionResponse>(this.url, data);
    }
  
    change(referral: Referral, id: number): Observable<ActionResponse> {
      return this.http.patch<ActionResponse>(this.url + "/" + id, referral);
    }
  
    delete(id: number) : Observable<Boolean> {
        return this.http.delete<Boolean>(this.url + '/' + id);
    }

    convertToContact(id: number): Observable<ActionResponse> {
      return this.http.post<ActionResponse>(this.url + "/" + id + "/convert", null);
    }
  
  }