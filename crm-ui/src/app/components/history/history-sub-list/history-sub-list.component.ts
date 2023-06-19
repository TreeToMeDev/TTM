import { Component, Input, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

import { HistoryService } from '../../../services/history.service';

@Component({
  selector: 'app-history-sub-list',
  templateUrl: './history-sub-list.component.html',
  styleUrls: ['./history-sub-list.component.css']
})
export class HistorySubListComponent implements OnInit {

  @Input() accountId: number;
  @Input() contactId: number;
  @Input() dealId: number;
  @Input() taskId: number;

  dataSource = new MatTableDataSource<History>();
  displayedColumns = [ 'action', 'description', 'timeStamp', 'userName'];
  history: History[];

  constructor(private historyService: HistoryService) { }

  ngOnInit(): void {
    if(this.accountId > 0) {
      this.historyService.fetchByAccount(this.accountId).subscribe(response => {
        this.history = response;
        this.dataSource.data = response;
      });
    }
    if(this.contactId > 0) {
      this.historyService.fetchByContact(this.contactId).subscribe(response => {
        this.history = response;
        this.dataSource.data = response;
      });
    }
    if(this.dealId > 0) {
      this.historyService.fetchByDeal(this.dealId).subscribe(response => {
        this.history = response;
        this.dataSource.data = response;
      });
    }
    if(this.taskId > 0) {
      this.historyService.fetchByTask(this.taskId).subscribe(response => {
        this.history = response;
        this.dataSource.data = response;
      });
    }
  }

}
