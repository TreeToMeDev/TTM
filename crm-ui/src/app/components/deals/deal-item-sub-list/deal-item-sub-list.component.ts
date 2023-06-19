import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Subject } from 'rxjs';

import { DealItem } from '../../../models/deal-item';
import { DealItemEditComponent } from '../deal-item-edit/deal-item-edit.component';
import { DealItemService } from '../../../services/deal-item.service';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-deal-item-sub-list',
  templateUrl: './deal-item-sub-list.component.html',
  styleUrls: ['./deal-item-sub-list.component.css']
})
export class DealItemSubListComponent implements OnInit {

  @Input() canEdit: boolean;
  @Input() updateDealId: Subject<number>;
  @Input() dealId: number;
  @Output() messageEvent = new EventEmitter<number>();

  displayedColumns: string[] = ['quantity', 'product', 'price', 'amount', 'buttons']

  dealItems: DealItem[];
  totalValue = 0;

  constructor(private dealItemService: DealItemService,
              private matDialog: MatDialog) { }

  ngOnInit(): void {
    if(typeof this.dealId == 'undefined') {
      this.dealId = -1;
    }
    this.updateDealId.subscribe( dealId => {
      this.dealItemService.saveNewDealDetails(dealId);
    })
    this.get();
  }

  get(): void {
    this.dealItemService.fetchByDeal(this.dealId).subscribe(result => {
      this.dealItems = result;
      this.updateTotal();
    });
  }

  // only available via dialog (I guess)
  addView(mode: string, event: any, id: number) : void {
    event.stopPropagation();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: id, // will be -1 for add
      dealId: this.dealId
    };
    let dialogRef = this.matDialog.open(DealItemEditComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        dialogRef.close();
        this.get();
      }
    );    
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string, id: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Line Item?" };
    const dialogRef = this.matDialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.dealItemService.delete(id).subscribe(success => {
            if(success) {
              this.get();
            }
          });
        }
      }
    );    
  }

  updateTotal() {
    this.totalValue = 0;
    this.dealItems.map(element => {
      this.totalValue += (element.quantity * element.price);
    });
    this.messageEvent.emit(this.totalValue);
  }

  getAmount(dealItem: DealItem) {
    return dealItem.quantity * dealItem.price;
  }

}