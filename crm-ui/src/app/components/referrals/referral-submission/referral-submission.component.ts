import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, EMPTY } from 'rxjs';
import { HttpErrorHandler } from '../../../http-error-handler';
import { ReferralFormService } from '../../../services/referral-form.service';

@Component({
  selector: 'app-referral-submission',
  templateUrl: './referral-submission.component.html',
  styleUrls: ['./referral-submission.component.css']
})
export class ReferralSubmissionComponent implements OnInit {

  form: FormGroup;
  formLoaded = false;
  
  referrerFirstName: string;
  referrerLastName: string;
  referrerEmail: string;
  referrerPhone: string;
  
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  companyName: string;
  jobTitle: string;

  notes: string;

  consentToContact: boolean = false;

  submitPressed = false;
  isSubmitting: boolean = false;
  isSubmitted: boolean = false;
  
  serverError: string;

  constructor(private formBuilder: FormBuilder,
              private referralFormService: ReferralFormService,
              private httpErrorHandler: HttpErrorHandler) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.form = this.formBuilder.group({
      referrerFirstName: [this.referrerFirstName, [Validators.required]],
      referrerLastName: [this.referrerLastName, [Validators.required]],
      referrerEmail: [this.referrerEmail, [Validators.required, Validators.email]],
      referrerPhone: [this.referrerPhone, [Validators.required]],
      firstName: [this.firstName, [Validators.required]],
      lastName: [this.lastName, [Validators.required]],
      companyName: [this.companyName, [Validators.required]],
      jobTitle: [this.jobTitle],
      email: [this.email, [Validators.required, Validators.email]],
      phone: [this.phone, [Validators.required]],
      notes: [this.notes],
      consentToContact: [this.consentToContact]
  })

    this.formLoaded = true;
  }

  onSubmit() {
    this.serverError = '';
    this.submitPressed = true;
    this.consentToContact = this.form.controls['consentToContact'].value;
    
    if(this.form.valid && this.consentToContact) {
      this.isSubmitting = true;

      this.referralFormService.add(this.form.value).pipe(
        catchError(error => {
          this.httpErrorHandler.handle(error, this);
          this.isSubmitting = false;
          this.isSubmitted = false;  
          return EMPTY;
        })
      ).subscribe(success => {
        this.isSubmitting = false;
        this.isSubmitted = true;
        this.form.disable();
      })
    } else {
      this.serverError = 'Please check errors and click Submit again.';
    }
  }
}
