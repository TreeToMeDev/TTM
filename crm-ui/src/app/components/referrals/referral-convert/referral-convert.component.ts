import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { catchError, of } from 'rxjs';
import { HttpErrorHandler } from 'src/app/http-error-handler';
import { Referral } from 'src/app/models/referral';
import { ReferralService } from 'src/app/services/referral.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-referral-convert',
  templateUrl: './referral-convert.component.html',
  styleUrls: ['./referral-convert.component.css']
})
export class ReferralConvertComponent implements OnInit {

  referral: Referral;
  id: number;
  userName: String;

  constructor(private referralService: ReferralService,
              private route: ActivatedRoute,
              private router: Router,
              private sessionService: SessionService,
              private httpErrorHandler: HttpErrorHandler) { }

  ngOnInit(): void {
    this.route.params.subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.referralService.fetch(this.id).subscribe(response => {
            this.referral = response;
          })
        }
    );

    this.userName = this.sessionService.getUserName();
  }

  onCancel() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  onSubmit() {
    this.referralService.convertToContact(this.id).pipe(
      catchError(error => {
      this.httpErrorHandler.handle(error, this);
      return of([]);
    })
    ).subscribe(success => {
        this.router.navigate(['referrals'])
    })
  }
}
