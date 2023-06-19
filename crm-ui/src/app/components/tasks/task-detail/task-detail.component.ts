import { ActivatedRoute, Params, Router } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';
import { EventEmitter, Output } from '@angular/core';
import { Task } from '../../../models/task'
import { TaskEditDialogComponent } from '../task-edit-dialog/task-edit-dialog.component';
import { TaskService } from '../../../services/task.service';

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css']
})

export class TaskDetailComponent implements OnInit {

  @Input() id: number;
  @Input() mode: string; // DIALOG or undefined
  @Output() closeAction = new EventEmitter<Boolean>();

  canComplete = true;
  task: Task;

  constructor(private dialog: MatDialog,
              private route: ActivatedRoute,
              private router: Router,
              private taskService: TaskService) {}

  ngOnInit(): void {
    this.route.params.subscribe(
        (params: Params) => {
          if(isNaN(this.id)) {
            this.id = params['id'];
          }
          this.taskService.fetch(this.id).subscribe(response => {
            this.task = response;
            this.canComplete = this.task.status != 'COMPLETE';
          })
        }
    );  
  }

  delete(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Task?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.taskService.delete(this.id).subscribe(success => {
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
      const dialogRef = this.dialog.open(TaskEditDialogComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        confirmed => {
          if(confirmed) {
            this.closeAction.emit(true);
          }
        }
      );
    } else {
      this.router.navigate(['tasks', id, 'edit'])
    }
  }

  close(refresh: boolean): void {
    if(this.mode == 'DIALOG') {
      this.closeAction.emit(refresh)
    } else {
      this.router.navigate(['tasks'])
    }
  }

  complete(): void {
    this.task.status = 'COMPLETE';
    this.taskService.save(this.task, this.id).subscribe(() => {
      this.close(true);
    });
  }
  
  completeAndCreate(): void {
    this.task.status = 'COMPLETE';
    this.taskService.save(this.task, this.id).subscribe(() => {
      if(this.mode == 'DIALOG') {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.data = {
          accountId: this.task.accountId,
          contactId: this.task.contactId
        };
        const dialogRef = this.dialog.open(TaskEditDialogComponent, dialogConfig);
        dialogRef.afterClosed().subscribe(
          confirmed => {
            if(confirmed) {
              this.closeAction.emit(true);
            }
          }
        );
      } else {
        this.router.navigate(['tasks', 'new'], {
          queryParams: { 
            accountId: this.task.accountId, 
            contactId: this.task.contactId
          }
        })
      }
    });
  }

}
