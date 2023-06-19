import { Account } from '../../../models/account'
import { AccountsService } from '../../../services/accounts.service'

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IndustriesMap } from '../../../enums/industry.enum';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute,
              private accountsService: AccountsService,
              private dialog: MatDialog,
              private router: Router) { }

  account: Account
  id: number

  industries = IndustriesMap;
  
  ngOnInit(): void {
    // some examples use 'subscribe' here, not sure what the difference is?
    // TODO standardize this and understand the difference
    this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.accountsService.fetch(this.id).subscribe(account => this.account = account);
  }
  
  goToUrl(site: string) {
    window.open('https://' + site, '_blank');
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Account?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.accountsService.delete(this.id).subscribe(success => {
            if(success) {
              this.router.navigate(['accounts'])
            }
          });
        }
      }
    );    
  }

}
