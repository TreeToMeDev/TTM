import { ActivatedRoute, Params, Router } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';
import { EMPTY, first, forkJoin, of, Subject } from 'rxjs';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

import { DealEditDialogComponent } from '../deal-edit-dialog/deal-edit-dialog.component';
import { DealStagesMap } from '../../../enums/deal-stages.enum';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';
import { EventEmitter, Output } from '@angular/core';
import { Deal } from '../../../models/deal'

//import { TaskEditDialogComponent } from '../task-edit-dialog/task-edit-dialog.component';
import { DealService } from '../../../services/deal.service';

@Component({
  selector: 'app-deal-detail',
  templateUrl: './deal-detail.component.html',
  styleUrls: ['./deal-detail.component.css']
})

export class DealDetailComponent implements OnInit {
  
  @Input() id: number;
  @Input() mode: string; // DIALOG or undefined
  @Output() closeAction = new EventEmitter<Boolean>();
  
  deal: Deal;
  dealStages = DealStagesMap;
  totalValue = 0;
  updatedDealId: Subject<number> = new Subject();

  constructor(private dealService: DealService,
              private dialog: MatDialog,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.route.params.subscribe(
        (params: Params) => {
          if(isNaN(this.id)) {
            this.id = params['id'];
          }
          this.dealService.fetch(this.id).subscribe(response => {
            this.deal = response;
            //this.canComplete = this.task.status != 'COMPLETE';
          })
        }
    );  
  }

  delete(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Deal?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.dealService.delete(this.id).subscribe(success => {
            if(success) {
              this.close(true);
            }
          });
        }
      }
    );    
  }

  edit(id: number): void {
    if(this.mode == 'DIALOG') {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = { id: id};
      const dialogRef = this.dialog.open(DealEditDialogComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        confirmed => {
          if(confirmed) {
            this.closeAction.emit(true);
          }
        }
      );
    } else {
      this.router.navigate(['deals', id, 'edit'])
    }
  }

  close(refresh: boolean): void {
    if(this.mode == 'DIALOG') {
      this.closeAction.emit(refresh)
    } else {
      this.router.navigate(['deals'])
    }
  }

  complete(): void {
    //this.task.status = 'COMPLETE';
    this.dealService.save(this.deal, this.id).subscribe(() => {
      this.close(true);
    });
  }
  
  completeAndCreate(): void {
    //this.task.status = 'COMPLETE';
    this.dealService.save(this.deal, this.id).subscribe(() => {
      if(this.mode == 'DIALOG') {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.data = {
          //accountId: this.task.accountId,
          //contactId: this.task.contactId
        };
        /*const dialogRef = this.dialog.open(TaskEditDialogComponent, dialogConfig);
        dialogRef.afterClosed().subscribe(
          confirmed => {
            if(confirmed) {
              this.closeAction.emit(true);
            }
          }
        );*/
      } else {
        this.router.navigate(['deals', 'new'], {
          queryParams: { 
            //accountId: this.task.accountId, 
            //contactId: this.task.contactId
          }
        })
      }
    });
  }

  receiveMessage($event) {
    this.totalValue = $event;
  }

}
