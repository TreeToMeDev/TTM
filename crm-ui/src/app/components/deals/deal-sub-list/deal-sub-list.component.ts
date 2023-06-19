import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';

import { Deal } from '../../../models/deal';
import { DealDetailDialogComponent } from '../deal-detail-dialog/deal-detail-dialog.component';
import { DealEditDialogComponent } from '../deal-edit-dialog/deal-edit-dialog.component';
import { DealService } from '../../../services/deal.service'
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-deal-sub-list',
  templateUrl: './deal-sub-list.component.html',
  styleUrls: ['./deal-sub-list.component.css']
})

export class DealSubListComponent implements OnInit {

  deals: Deal[];
  displayedColumns: string[] = ['name'];
  editNote: boolean;
  dataSource = new MatTableDataSource<Deal>();
  form: FormGroup;
  filterValue: string = 'open';

  isGettingTasks = false;

  @Input()
  accountId: number

  @Input()
  contactId: number

  constructor(private dealService: DealService,
              private dialog: MatDialog,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      filterShowCompleted: [false]
    })

    this.loadAll();
  }

  loadAll() {
    if (this.accountId > 0) {
      this.dealService.fetchAllByAccount(this.accountId, this.filterValue).subscribe(response => {
        this.deals = response;
        this.dataSource.data = response;
      });  
    } else if (this.contactId > 0) {
      this.dealService.fetchAllByContact(this.contactId, this.filterValue).subscribe(response => {
        this.deals = response;
        this.dataSource.data = response;
      });  
    } else {
      this.dealService.fetchAll(this.filterValue).subscribe(response => {
        this.deals = response;
        this.dataSource.data = response;
      });
    }
  }

  addView(mode: string, event: any, id: number) : void {
    event.stopPropagation();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      accountId: this.accountId,
      contactId: this.contactId,
      id: id, // will be -1 for add
    };
    let dialogRef;
    if(mode === 'ADD') {
      dialogRef = this.dialog.open(DealEditDialogComponent, dialogConfig);
    } else {
      dialogRef = this.dialog.open(DealDetailDialogComponent, dialogConfig);
    }
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.loadAll();
        }
      }
    );    
  }

  toggleCompleted() 
  {
    if (this.form.value.filterShowCompleted) {
      this.filterValue = 'all';  
    } else {
      this.filterValue = 'open';
    }

    this.loadAll();
   }
}
