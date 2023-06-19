import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { FileUploadResponse } from '../models/file-upload-response';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  url: string = environment.apiUrl + 'rest/file';
  
  constructor(private http: HttpClient) { }
  
  uploadFile(file: File): Observable<FileUploadResponse> {
    const data: FormData = new FormData();
    data.append('file', file);
        
    return this.http.post<FileUploadResponse>(this.url + "/upload/contacts", data);
  }

  postFile(fileCode: string): Observable<FileUploadResponse> {
    const data: FormData = new FormData();
    data.append('fileCode', fileCode);
        
    return this.http.post<FileUploadResponse>(this.url + "/post/contacts", data);
  }
}
