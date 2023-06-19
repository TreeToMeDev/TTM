import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-task-detail-dialog',
  templateUrl: './task-detail-dialog.component.html',
  styleUrls: ['./task-detail-dialog.component.css']
})

export class TaskDetailDialogComponent implements OnInit {
  
  id: number;

  constructor(private dialogRef: MatDialogRef<TaskDetailDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) { 
    this.id = data.id;
  }

  ngOnInit() {
  }

  close(event: any) {
    this.dialogRef.close(event)
  }

}
