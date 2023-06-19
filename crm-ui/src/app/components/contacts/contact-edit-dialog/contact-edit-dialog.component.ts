import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-contact-edit-dialog',
  templateUrl: './contact-edit-dialog.component.html',
  styleUrls: ['./contact-edit-dialog.component.css']
})

export class ContactEditDialogComponent implements OnInit {
  
  accountId: number;
  contactId: number;
  id: number;

  constructor(private dialogRef: MatDialogRef<ContactEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) { 
    this.accountId = data.accountId;
    this.contactId = data.contactId;
    this.id = data.id;
  }

  ngOnInit() {
  }

  close(event: any) {
    this.dialogRef.close(event)
  }

}
