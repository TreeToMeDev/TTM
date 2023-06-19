import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Attachment } from "../models/attachment";
import { AttachmentDownload } from "../models/attachment-download";

@Injectable({
    providedIn: 'root'
})
  
export class AttachmentService {
  
    url: string = environment.apiUrl + 'rest/attachments/';
  
    constructor(private httpClient: HttpClient) { }
  
    fetch(accountId: number): Observable<Attachment[]> {
      return this.httpClient.get<Attachment[]>(this.url + 'accounts/' + accountId);
    }
  
    fetchOne(id: number): Observable<Attachment> {
      return this.httpClient.get<Attachment>(this.url + id);
    }
  
    add(accountId: number, file: File) {
      const data: FormData = new FormData();
      data.append('file', file);
      return this.httpClient.post(this.url + 'accounts/' + accountId, data);
    }

    delete(id: number) : Observable<Boolean> {
      return this.httpClient.delete<Boolean>(this.url + id);
    }
  
    downloadAttachmentLink(accountId: number, attachmentId: number): Observable<AttachmentDownload> {
      return this.httpClient.get<AttachmentDownload>(this.url + 'accounts/' + accountId + 
                                      "/attachments/" + attachmentId);
    }
  }