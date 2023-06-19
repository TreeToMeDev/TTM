import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { CSVFileStatus } from '../../../enums/csv-file-status.enum';
import { UploadEntityType, UploadEntityTypeMap } from '../../../enums/upload-entity-type.enum';
import { CSVFileDetails } from '../../../models/csv-file-details';
import { CSVUploadResponse } from '../../../models/csv-upload-response';
import { FileUpload } from '../../../models/file-upload';
import { FileUploadService } from '../../../services/file-upload.service';

@Component({
  selector: 'app-file-uploader',
  templateUrl: './file-uploader.component.html',
  styleUrls: ['./file-uploader.component.css']
})
export class FileUploaderComponent implements OnInit {

  uploadEntityTypes = UploadEntityTypeMap;
  selectedFileType: UploadEntityType;
  
  csvFileStatus = CSVFileStatus;
  
  @ViewChild("fileDropRef", { static: false }) fileDropEl: ElementRef;
  file: File = null;

  fileName = '';
  
  csvUploadResponse: CSVUploadResponse;
  fileUpload: FileUpload;
  fileDetails: CSVFileDetails;
  errors: string[];
  postCount: number;

  postResponse: string;

  isUploading: boolean = false;
  isPosting: boolean = false;

  // stages
  fileUploaded: boolean = false;
  filePosted: boolean = false;

  constructor(private fileUploadService: FileUploadService) { }
  
  ngOnInit(): void {
    this.selectedFileType = UploadEntityType.CONTACTS;
  }
  
  // handle file from browsing
  fileBrowseHandler(event: Event) {
    const target = event.target as HTMLInputElement;
    this.file = target.files[0];
    this.prepareFilesList(target.files[0]);
  }

  prepareFilesList(file: any) {
    this.fileDropEl.nativeElement.value = "";
    this.uploadFile();
  }

  uploadFile() {
    this.isUploading = true;
    this.fileUploadService.uploadFile(this.file).subscribe(fileUploadResponse => {
      this.csvUploadResponse = fileUploadResponse.csvResponse;
      this.fileDetails = fileUploadResponse.csvResponse.fileDetails;
      this.fileUpload = fileUploadResponse.csvResponse.fileUpload;
      this.errors = fileUploadResponse.csvResponse.errors;
      
      this.isUploading = false;
      this.fileUploaded = true;
    }
  )}

  postFile() {
    this.isPosting = true;
    this.fileUploadService.postFile(this.fileUpload.fileCode).subscribe(response => {
      this.postCount = response.csvResponse.postCount;
      this.isPosting = false;
      this.filePosted = true;
    });
  }

  deleteFile() {
    this.formReset();
  }

  //  Format size for display
  formatBytes(bytes, decimals = 2) {
    if (bytes === 0) {
      return "0 Bytes";
    }
    const k = 1024;
    const dm = decimals <= 0 ? 0 : decimals;
    const sizes = ["Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + " " + sizes[i];
  }

  formReset() {
    this.file = null;
    this.fileDetails = null;
    this.fileUploaded = false;
    this.filePosted = false;
    this.postCount = 0;
  }
}
