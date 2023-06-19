import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';

import { AssignedToMap } from '../../../enums/assigned-to.enum';
import { Deal } from '../../../models/deal';
import { DealService } from '../../../services/deal.service';
import { DealStagesMap } from '../../../enums/deal-stages.enum';
import { getEnumKeyByEnumValue } from '../../../enums/enum-functions';
import { getEnumValueByEnumKey } from '../../../enums/enum-functions';
import { SessionService } from '../../../services/session.service';
import { TaskStatus } from '../../../enums/task-statuses.enum';

@Component({
  selector: 'app-deal-list',
  templateUrl: './deal-list.component.html',
  styleUrls: ['./deal-list.component.css']
})

export class DealListComponent implements OnInit {

  deals: Deal[] = []
  displayedColumns: string[] = ['name', 'accountName', 'stage', 'dueDate'];
  
  assignedToFilters = AssignedToMap;
  currentCompletedTasksFilterValue = false;
  contactServiceSub: Subscription;
  dataSource = new MatTableDataSource<Deal>();
  dealStages = DealStagesMap;
  filterForm: FormGroup;
  getEnumValueByEnumKey = getEnumValueByEnumKey;
  isLoading: boolean = true;

  constructor(private dealService: DealService,
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
        this.dealService.fetchAll('open').subscribe(deals => {
          this.deals = deals;
          this.dataSource.data = deals;
          this.isLoading = false;
        });
      }
    );
  }

  applyFilter() {
    this.dataSource.data = this.deals;
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

      this.isLoading = true;
      
      this.dealService.fetchAll(filterValue).subscribe(deals => {
        this.deals = deals;;
        this.dataSource.data = deals;
        this.isLoading = false;
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
