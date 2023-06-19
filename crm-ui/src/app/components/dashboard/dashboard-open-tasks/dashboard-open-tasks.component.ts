import { AfterViewInit, Component, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { getEnumKeyByEnumValue } from 'src/app/enums/enum-functions';
import { TaskStatus } from 'src/app/enums/task-statuses.enum';
import { Task } from 'src/app/models/task';
import { DashboardService } from 'src/app/services/dashboard.service';

@Component({
  selector: 'app-dashboard-open-tasks',
  templateUrl: './dashboard-open-tasks.component.html',
  styleUrls: ['./dashboard-open-tasks.component.css']
})
export class DashboardOpenTasksComponent implements OnInit {

  @Input() userId: number;

  tasks: Task[] = []
  displayedColumns: string[] = ['description', 'contactName', 'dueDate'];
  dataSource = new MatTableDataSource<Task>();
  
  isLoading: boolean = false;
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.setup();
  }

  ngOnChanges(changes: SimpleChanges): void {
    //Called before any other lifecycle hook. Use it to inject dependencies, but avoid any serious work here.
    //Add '${implements OnChanges}' to the class.
    this.setup(); 
  }

  setup() {
    this.isLoading = true;
    this.dashboardService.fetchOpenTasks(this.userId).subscribe(tasks => {
      this.tasks = tasks;
      this.dataSource.data = this.tasks;
      this.isLoading = false;
    })
  }

  checkDate(status, taskDate) {
    let statusEnum = getEnumKeyByEnumValue(TaskStatus, TaskStatus.NEW);
    return status == statusEnum && taskDate < new Date();
  }
}
