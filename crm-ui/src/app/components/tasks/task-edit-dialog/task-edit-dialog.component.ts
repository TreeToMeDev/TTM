import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-task-edit-dialog',
  templateUrl: './task-edit-dialog.component.html',
  styleUrls: ['./task-edit-dialog.component.css']
})

export class TaskEditDialogComponent implements OnInit {
  
  accountId: number;
  contactId: number;
  id: number;

  constructor(private dialogRef: MatDialogRef<TaskEditDialogComponent>,
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
