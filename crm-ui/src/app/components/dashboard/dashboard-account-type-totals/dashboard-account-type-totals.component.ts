import { Component, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ChartData, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { AppSettings } from 'src/app/core/settings/app-settings';
import { AccountTypeTotal } from 'src/app/models/account-type-total';
import { DashboardService } from 'src/app/services/dashboard.service';

@Component({
  selector: 'app-dashboard-account-type-totals',
  templateUrl: './dashboard-account-type-totals.component.html',
  styleUrls: ['./dashboard-account-type-totals.component.css']
})
export class DashboardAccountTypeTotalsComponent implements OnInit {

  @Input() userId: number;
  
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  isLoading: boolean = false;

  accountTypeTotals: AccountTypeTotal[];

  chartType: ChartType = 'pie';
  pieChartData: ChartData;
  pieChartOptions: ChartOptions;
  
  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.setup();
  }
  setup() {
    this.isLoading = true;
    this.dashboardService.fetchAccountTypeTotals(this.userId).subscribe(totals => {
      this.accountTypeTotals = totals;
  
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
        labels: this.accountTypeTotals.map(a => {
          if (a.accountType == '') {
            return 'Undefined';
          }
          return a.accountType;
        }),
        datasets: [ {
          data: this.accountTypeTotals.map(a => a.total),
          backgroundColor: AppSettings.CHART_COLORS
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
