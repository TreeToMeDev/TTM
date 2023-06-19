import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { catchError, EMPTY } from 'rxjs';
import { HttpErrorHandler } from 'src/app/http-error-handler';
import { Attachment } from 'src/app/models/attachment';
import { AttachmentService } from 'src/app/services/attachment.service';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-attachment-sub-list',
  templateUrl: './attachment-sub-list.component.html',
  styleUrls: ['./attachment-sub-list.component.css']
})
export class AttachmentSubListComponent implements OnInit {

  displayedColumns: string[] = ['name', 'size', 'userName', 'createTimestamp', 'buttons']
  editNote: boolean;
  attachments: Attachment[];
  
  isGettingAttachments = false;
  fileAction: boolean = false;

  @Input()
  accountId: number

  @Input()
  contactId: number

  @ViewChild('file')
  fileElement: ElementRef;

  constructor(private dialog: MatDialog,
              private attachmentService: AttachmentService,
              private httpErrorHandler: HttpErrorHandler) { }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.attachmentService.fetch(this.accountId).subscribe(attachments => {
      this.attachments = attachments;
    });  
  }

  addAttachment(event) {
    const file: File = event.target.files[0];

    if (file) {
      this.fileAction = true;
      this.attachmentService.add(this.accountId, file).pipe(
        catchError(error => {
          console.log("A sever error");
          this.httpErrorHandler.handle(error, this);
          this.fileAction = false;
        this.resetFileLoader();
          return EMPTY;
        })
      ).subscribe(data => {
        this.attachmentService.fetch(this.accountId).subscribe(attachments => {
          this.attachments = attachments;
          this.fileAction = false;
          this.resetFileLoader();
        })
      })
    }
  }

  // TODO: clean this up to make it a smaller footprint, used in many places
  openDialog(enterAnimationDuration: string, exitAnimationDuration: string, id: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Attachment?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.fileAction = true;

          this.attachmentService.delete(id).subscribe(success => {
            if(success) {
              // plan A was to remove just-deleted item from local array but I couldn't
              // get view to repaint properly even with ChangeDetectorRef, etc. So do a 
              // full reload --- works, but user loses their scroll, which blows donkey balls.
              this.attachmentService.fetch(this.accountId).subscribe(attachments => {
                this.fileAction = false;
                this.attachments = attachments;
              });
            } else {
              // TODO report errors properly
              alert('ERROR deleting Attachment');
              this.fileAction = false;
            }
          });
        }
      }
    );    
  }

  fetchAttachment(id: number) {
    this.fileAction = true;
    this.attachmentService.downloadAttachmentLink(this.accountId, id).subscribe(data => {
      this.fileAction = false;
      window.open(data.webLink);
    })
  }

  resetFileLoader() {
    this.fileElement.nativeElement.value = "";
  }
}
