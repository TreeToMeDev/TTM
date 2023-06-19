import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-deal-edit-dialog',
  templateUrl: './deal-edit-dialog.component.html',
  styleUrls: ['./deal-edit-dialog.component.css']
})

export class DealEditDialogComponent implements OnInit {
  
  accountId: number;
  contactId: number;
  id: number;

  constructor(private dialogRef: MatDialogRef<DealEditDialogComponent>,
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
