import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-purchase-edit-dialog',
  templateUrl: './purchase-edit-dialog.component.html',
  styleUrls: ['./purchase-edit-dialog.component.css']
})

export class PurchaseEditDialogComponent implements OnInit {
  
  accountId: number;
  id: number;

  constructor(private dialogRef: MatDialogRef<PurchaseEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) { 
    this.accountId = data.accountId;
    this.id = data.id;
  }

  ngOnInit() {
  }

  close(event: any) {
    this.dialogRef.close(event)
  }

}
