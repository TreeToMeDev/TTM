import { Component, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Contact } from 'src/app/models/contact';
import { Task } from 'src/app/models/task';
import { DashboardService } from 'src/app/services/dashboard.service';

@Component({
  selector: 'app-dashboard-new-leads',
  templateUrl: './dashboard-new-leads.component.html',
  styleUrls: ['./dashboard-new-leads.component.css']
})
export class DashboardNewLeadsComponent implements OnInit {

  @Input() userId: number;

  contacts: Contact[] = []
  displayedColumns: string[] = ['firstName', 'accountName', 'email', 'phone'];
  dataSource = new MatTableDataSource<Contact>();
  
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
    this.dashboardService.fetchNewLeads(this.userId).subscribe(contacts => {
      this.contacts = contacts;
      this.dataSource.data = this.contacts;
      this.isLoading = false;
    })
  }
}
