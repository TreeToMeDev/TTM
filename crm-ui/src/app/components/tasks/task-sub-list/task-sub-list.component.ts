import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';

import { Task } from '../../../models/task';
import { TaskDetailDialogComponent } from '../task-detail-dialog/task-detail-dialog.component';
import { TaskEditDialogComponent } from '../task-edit-dialog/task-edit-dialog.component';
import { TaskService } from '../../../services/task.service'
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-task-sub-list',
  templateUrl: './task-sub-list.component.html',
  styleUrls: ['./task-sub-list.component.css']
})

export class TaskSubListComponent implements OnInit {

  displayedColumns: string[] = ['description', 'dueDate', 'status', 'priority']
  editNote: boolean
  tasks: Task[]
  dataSource = new MatTableDataSource<Task>();
  form: FormGroup;
  filterValue: string = 'open';

  isGettingTasks = false;

  @Input() accountId: number
  @Input() contactId: number

  constructor(private dialog: MatDialog,
              private taskService: TaskService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      filterShowCompleted: [false]
    })

    this.loadAll();
  }

  loadAll() {
    if (this.accountId > 0) {
      this.taskService.fetchAllByAccount(this.accountId, this.filterValue).subscribe(tasks => {
        this.tasks = tasks;
        this.dataSource.data = tasks;
      });  
    } else if (this.contactId > 0) {
      this.taskService.fetchAllByContact(this.contactId, this.filterValue).subscribe(tasks => {
        this.tasks = tasks;
        this.dataSource.data = tasks;
      });  
    } else {
      this.taskService.fetchAll(this.filterValue).subscribe(tasks => {
        this.tasks = tasks;
        this.dataSource.data = tasks;
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
      dialogRef = this.dialog.open(TaskEditDialogComponent, dialogConfig);
    } else {
      dialogRef = this.dialog.open(TaskDetailDialogComponent, dialogConfig);
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
