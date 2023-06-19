import { Component, OnInit } from '@angular/core';
import { IndustriesMap } from '../../../enums/industry.enum';

import { Account } from '../../../models/account';
import { AccountsService } from '../../../services/accounts.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { SessionService } from '../../../services/session.service';
import { EntityOwner, EntityOwnersMap } from '../../../enums/entity-owner.enum';
import { CsvDownloadService } from 'src/app/services/csv-download.service';

@Component({
  selector: 'account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})

export class AccountListComponent implements OnInit {

  accounts: Account[];
  dataSource = new MatTableDataSource<Account>();
  entityOwnerFilters = EntityOwnersMap;
  filterForm: FormGroup;

  industries = IndustriesMap;
  
  displayedColumns: string[] = ['name', 'industry', 'billingProvinceState', 'billingCountry', 'accountType', 'agentName', 'lastChange']

  exportColumns: string[] = ['name', 'industry', 'billingProvinceState', 'billingCountry', 'accountType', 'agentName', 'lastChangeTimestamp'];
  
  constructor(private accountsService: AccountsService,
              private formBuilder: FormBuilder,
              private sessionService: SessionService,
              private csvDownloadService: CsvDownloadService) {}
  
  ngOnInit(): void {
	  /*this.accountsService.fetchAll().pipe(catchError(error => {
        console.log('THERE WAS AN ERROR!');
        return of([]);
      })).subscribe(accounts => {
      this.accounts = accounts
      this.dataSource.data = accounts;
      this.applyFilter();
    })*/
    this.accountsService.fetchAll().subscribe(accounts => {
      this.accounts = accounts;
      this.dataSource.data = accounts;
      this.applyFilter();
    })
    this.setFilterForm();
  }

  public setFilterForm() {
    this.filterForm = this.formBuilder.group({
      filterEntityOwner: new FormControl(EntityOwner.MINE_ONLY)
    });
  }
  
  
  applyFilter() {
    this.dataSource.data = this.accounts;
    
    this.dataSource.data = this.dataSource.data.filter(e => {
      switch (this.filterForm.value.filterEntityOwner) {
        case EntityOwner.ALL:
          return true;
        case EntityOwner.MINE_ONLY:
          return e.agentName == this.sessionService.getUserName();
        case EntityOwner.NOT_ASSIGNED:
          return e.agentName == 'Not assigned';
      }
      return true;
    })
  }

  resetFilterForm() {
    this.filterForm.reset({ filterEntityOwner: EntityOwner.MINE_ONLY });
    this.applyFilter();
  }

  exportTable() {
    this.csvDownloadService.exportToCsv("syker-account-export.csv", this.dataSource.data, this.exportColumns);
  }

}
