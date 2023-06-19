import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-deal-detail-dialog',
  templateUrl: './deal-detail-dialog.component.html',
  styleUrls: ['./deal-detail-dialog.component.css']
})

export class DealDetailDialogComponent implements OnInit {
  
  id: number;

  constructor(private dialogRef: MatDialogRef<DealDetailDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) { 
    this.id = data.id;
  }

  ngOnInit() {
  }

  close(event: any) {
    this.dialogRef.close(event)
  }

}
