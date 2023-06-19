import { Component, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ChartData, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { AppSettings } from 'src/app/core/settings/app-settings';
import { AccountCountryTotal } from 'src/app/models/account-country-total';
import { DashboardService } from 'src/app/services/dashboard.service';

@Component({
  selector: 'app-dashboard-account-country-totals',
  templateUrl: './dashboard-account-country-totals.component.html',
  styleUrls: ['./dashboard-account-country-totals.component.css']
})
export class DashboardAccountCountryTotalsComponent implements OnInit {

  @Input() userId: number;
  
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  isLoading: boolean = false;

  accountCountryTotals: AccountCountryTotal[];

  chartType: ChartType = 'pie';
  pieChartData: ChartData;
  pieChartOptions: ChartOptions;
  
  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.setup();
  }

  setup() {
    this.isLoading = true;
    this.dashboardService.fetchAccountCountryTotals(this.userId).subscribe(totals => {
      this.accountCountryTotals = totals;
  
      this.pieChartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: true,
            position: 'right',
            labels: {
              font: {
                size: 20
              }
            }
          }
        }
      };

      this.pieChartData = {
        labels: this.accountCountryTotals.map(a => {
          if (a.country == '') {
            return 'Undefined';
          }
          return a.country;
        }),
        datasets: [ {
          data: this.accountCountryTotals.map(a => a.total),
          backgroundColor: AppSettings.CHART_COLORS,
        } ]
      }

      this.isLoading = false;
    })
    
  }

  ngOnChanges(changes: SimpleChanges): void {
    //Called before any other lifecycle hook. Use it to inject dependencies, but avoid any serious work here.
    //Add '${implements OnChanges}' to the class.
    this.setup(); 
  }
}

