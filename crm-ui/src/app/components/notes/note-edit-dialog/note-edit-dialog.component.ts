import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-note-edit-dialog',
  templateUrl: './note-edit-dialog.component.html',
  styleUrls: ['./note-edit-dialog.component.css']
})

export class NoteEditDialogComponent implements OnInit {
  
  accountId: number;
  contactId: number;
  id: number;
  referralId: number

  constructor(private dialogRef: MatDialogRef<NoteEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) { 
    this.accountId = data.accountId;
    this.contactId = data.contactId;
    this.id = data.id;
    this.referralId = data.referralId;
  }

  ngOnInit() {
  }

  close(event: any) {
    this.dialogRef.close(event)
  }

}
