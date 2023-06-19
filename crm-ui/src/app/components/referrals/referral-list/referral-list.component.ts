import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs';
import { Referral } from 'src/app/models/referral';
import { ReferralService } from 'src/app/services/referral.service';

@Component({
  selector: 'app-referral-list',
  templateUrl: './referral-list.component.html',
  styleUrls: ['./referral-list.component.css']
})
export class ReferralListComponent implements OnInit {

  @ViewChild('referralTbSort') referralTbSort = new MatSort();
  
  referrals: Referral[] = [];
  displayedColumns: string[] = ['firstName', 'email', 'phone', 'companyName', 'jobTitle', 'referrerName', 'submitTimestamp'];
  
  isLoading: boolean = true;
  referralServiceSub: Subscription;
  dataSource = new MatTableDataSource<Referral>();
  filterForm: FormGroup;

  constructor(private referralService: ReferralService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadAllReferrals();
    this.setFilterForm();
    this.dataSource.sortingDataAccessor = (data: any, sortHeaderId: string): string => {
      if (typeof data[sortHeaderId] === 'string') {
        return data[sortHeaderId].toLocaleLowerCase();
      }
    
      return data[sortHeaderId];
    };
  }

  ngAfterViewInit() {    
    this.dataSource.sort = this.referralTbSort;
  }

  public setFilterForm() {
    this.filterForm = this.formBuilder.group({
      filterName: new FormControl(null)
    });

    this.applyFilter();
  }

  loadAllReferrals() {
    this.isLoading = true;
    this.route.params.subscribe(
      (params: Params) => {
        this.referralServiceSub = this.referralService.fetchAll('').subscribe(referrals => {
          this.referrals = referrals;
          this.dataSource.data = referrals;
          this.isLoading = false;
          //  Now that the data is here we can apply the search filter if we have some
          //  defaults set
          this.applyFilter();
        });
      }
    );
  }

  applyFilter() {
    this.dataSource.data = this.referrals;

    if (this.filterForm.value.filterName) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        return e.firstName.toLowerCase().includes(this.filterForm.value.filterName.toLowerCase()) ||
               e.lastName.toLowerCase().includes(this.filterForm.value.filterName.toLowerCase());
      });
    }
  }

  resetFilterForm() {
    this.applyFilter();
  }

}
