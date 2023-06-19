import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Task } from '../../../models/task';
import { TaskService } from '../../../services/task.service';
import { SessionService } from '../../../services/session.service';
import { AssignedToMap } from '../../../enums/assigned-to.enum';
import { TaskStatus } from '../../../enums/task-statuses.enum';
import { getEnumKeyByEnumValue } from '../../../enums/enum-functions';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})

export class TaskListComponent implements OnInit {

  tasks: Task[] = []
  displayedColumns: string[] = ['description', 'contactName', 'dueDate', 'status', 'priority'];
  
  isLoading: boolean = true;
  isGettingTasks = false;
  currentCompletedTasksFilterValue = false;
  contactServiceSub: Subscription;
  dataSource = new MatTableDataSource<Task>();
  filterForm: FormGroup;
  assignedToFilters = AssignedToMap;

  constructor(private taskService: TaskService,
              private sessionService: SessionService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadAll();
    this.setFilterForm();
  }

  public setFilterForm() {
    this.filterForm = this.formBuilder.group({
      filterAssignedTo: new FormControl(null),
      filterShowCompleted: new FormControl(false)
    });

    this.currentCompletedTasksFilterValue = false;
  }

  loadAll() {
    this.isLoading = true;
    this.route.params.subscribe(
      (params: Params) => {
        this.taskService.fetchAll('open').subscribe(tasks => {
          this.tasks = tasks;
          this.dataSource.data = tasks;
          this.isLoading = false;
        });
      }
    );
  }

  applyFilter() {
    this.dataSource.data = this.tasks;
    if (this.filterForm.value.filterAssignedTo) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        var includeUnassigned = e.agentId < 0 && this.filterForm.value.filterAssignedTo == 'NOT_ASSIGNED'
        // TODO should use ID here, not name, ffs
        var includeMineOnly = e.agentName == this.sessionService.getUserName() && this.filterForm.value.filterAssignedTo == 'MINE_ONLY'
        var includeAnybody = e.agentId > 0 && this.filterForm.value.filterAssignedTo == 'ANYBODY'
        return includeUnassigned || includeMineOnly || includeAnybody
      });
    }

    if (this.filterForm.value.filterShowCompleted != this.currentCompletedTasksFilterValue) {
  
      var filterValue = "";
      
      if (this.filterForm.value.filterShowCompleted) {
        filterValue = 'all';  
      } else {
        filterValue = 'open';
      }

      this.isGettingTasks = true;
      
      this.taskService.fetchAll(filterValue).subscribe(tasks => {
        this.tasks = tasks;
        this.dataSource.data = tasks;
        this.isGettingTasks = false;
        this.currentCompletedTasksFilterValue = this.filterForm.value.filterShowCompleted;
        
        //  Have to apply filter again because the http call will return all tasks
        //  and the filtering happens on the client.  Since we are maing another
        //  call we have to apply the other filters again
        this.applyFilter();
      });

    }
  }

  resetFilterForm() {
    this.filterForm.reset({ filterShowCompleted: false });
    this.applyFilter();
  }
  
  checkDate(status, taskDate) {
    let statusEnum = getEnumKeyByEnumValue(TaskStatus, TaskStatus.NEW);
    return status == statusEnum && taskDate < new Date();
  }
}
