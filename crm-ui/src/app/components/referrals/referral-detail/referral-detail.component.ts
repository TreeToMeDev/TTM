import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Referral } from '../../../models/referral';
import { ReferralService } from '../../../services/referral.service';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-referral-detail',
  templateUrl: './referral-detail.component.html',
  styleUrls: ['./referral-detail.component.css']
})
export class ReferralDetailComponent implements OnInit {

  referral: Referral;
  id: number;

  constructor(private referralService: ReferralService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.params.subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.referralService.fetch(this.id).subscribe(response => {
            this.referral = response;
          })
        }
    );
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Referral?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.referralService.delete(this.id).subscribe(success => {
            if(success) {
              this.router.navigate(['referrals'])
            }
          });
        }
      }
    );    
  }
}
