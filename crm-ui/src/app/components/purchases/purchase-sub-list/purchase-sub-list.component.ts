import { ActivatedRoute, Router } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';

import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Purchase } from '../../../models/purchase';
import { PurchaseEditDialogComponent } from '../purchase-edit-dialog/purchase-edit-dialog.component';
import { PurchaseService } from '../../../services/purchase.service';

@Component({
  selector: 'app-purchase-sub-list',
  templateUrl: './purchase-sub-list.component.html',
  styleUrls: ['./purchase-sub-list.component.css']
})

export class PurchaseSubListComponent implements OnInit {
  
  @Input() accountId: number

  displayedColumns: string[] = ['productCode', 'datePurchasedString', 'serial', 'warrantyEndDateString', 'buttons']
  purchases: Purchase[]

  constructor(
    private activatedRoute: ActivatedRoute,
    private dialog: MatDialog,
	  private purchaseService: PurchaseService,
    private router: Router) { }

  ngOnInit(): void {
	  // TODO: refresh occurs even after CXL out of update, which is not ideal
    this.activatedRoute.params.subscribe(params => {
      this.get();
	  });
  }  
  
  get(): void {
	  this.purchaseService.get(this.accountId).subscribe(purchases => {
		  this.purchases = purchases
	  });
  }
 
  add(): void {
    this.purchaseService.setActiveRecord({} as Purchase);
  }

  change(purchase: Purchase): void {
	  this.purchaseService.setActiveRecord(purchase);
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string, id: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Purchase?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.purchaseService.delete(id).subscribe(success => {
            if(success) {
              this.get();
            } else {
              // TODO report errors properly
              alert('ERROR deleting Purchase');
            }
          });
        }
      }
    );    
  }

  addView(mode: string, event: any, id: number) : void {
    event.stopPropagation();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {};
    dialogConfig.data.accountId = this.accountId;
    dialogConfig.data.id = id; // -1 for ADD
    let dialogRef = this.dialog.open(PurchaseEditDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          // TODO share with ngInit
          this.purchaseService.get(this.accountId).subscribe(purchases => this.purchases = purchases)
        }
      }
    );    
  }

}