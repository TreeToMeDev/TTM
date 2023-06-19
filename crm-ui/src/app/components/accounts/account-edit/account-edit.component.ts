import { ActivatedRoute, Router } from '@angular/router';
import { catchError, first } from 'rxjs/operators'
import { Component, OnInit } from '@angular/core';
import { EMPTY, forkJoin, of } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';

import { Account } from '../../../models/account';
import { AccountsService } from '../../../services/accounts.service';
import { Agent } from '../../../models/agent';
import { AgentService } from '../../../services/agent.service';
import { HttpErrorHandler } from '../../../http-error-handler'
import { IndustriesMap } from '../../../enums/industry.enum';
import { SessionService } from '../../../services/session.service';
import { CurrencyService } from '../../../services/currency.service';
import { Currency } from '../../../models/currency';

// TODO: share publicly
enum Mode {
  ADD, DUPLICATE, EDIT
}

@Component({
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.css']
})

export class AccountEditComponent implements OnInit {

  account: Account;
  agentList: Agent[];
  formLoaded = false;
  header = '';
  id: number;
  industries = IndustriesMap;
  mode: Mode;
  serverError: String;
  currencyList: Currency[];
  
  form = this.formBuilder.group({
    accountType: ['', [Validators.required]],
    agentId: ['', [Validators.required]],
    billingCity: [''],
    billingCountry: [''],
    billingPostalCode: [''],
    billingProvinceState: [''],
    billingStreet: [''],
    currency: ['', [Validators.required]],
    industry: [''],
    name: ['', [Validators.required]],
    phone: [''],
    shippingCity: [''],
    shippingPostalCode: [''],
    shippingProvinceState: [''],
    shippingStreet: [''],
    webSite: [''],
    shippingCountry: [''],
    shippingAddressSameAsBilling: ['']
  });

  constructor(private accountsService: AccountsService,
              private agentService: AgentService,
              private httpErrorHandler: HttpErrorHandler,
              private formBuilder: FormBuilder, 
              private route: ActivatedRoute, 
              private router: Router,
              private sessionService: SessionService,
              private currencyService: CurrencyService) {}

  ngOnInit(): void {
    this.mode = Mode.ADD;
    if(this.router.url.endsWith('duplicate')) {
      this.mode = Mode.DUPLICATE
    }
    if(this.router.url.endsWith('edit')) {
      this.mode = Mode.EDIT;
    }
    this.header = Mode[this.mode] + ' Account';
    let agentsObservable = this.agentService.fetchAll().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error,this)
        return EMPTY;
      })
    );
    let currenciesObservable = this.currencyService.fetchAllCurrencies().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error,this)
        return EMPTY;
      })
    );
    forkJoin([this.route.params.pipe(first()), agentsObservable, currenciesObservable]).subscribe(results => {
      this.id = results[0]['id'];
      this.agentList = this.agentService.buildList(results[1], 0, 0);
      this.currencyList = results[2];
      this.currencyService.sortByDescription(this.currencyList);
       if (this.mode == Mode.EDIT || this.mode == Mode.DUPLICATE) {
          this.accountsService.fetch(this.id).subscribe(response => {
          this.form.patchValue(response);
          this.onShippingSameChange();
          if(this.mode == Mode.EDIT) {
            this.header += ' - ' + this.form.get('name')?.value;
          }
        })
      }
    })
    // TODO this should only be on newly created Accounts, not Edits?
    var userId = this.sessionService.getUserId();
    this.form.get('agentId')?.setValue(userId);
  }

  onSubmit() {
    this.serverError = '';
    if(this.mode == Mode.DUPLICATE) {
      this.id = -1;
    }
    if(this.form.valid) {
      this.accountsService.save(this.form.value, this.id).pipe(
        catchError(error => {
          this.httpErrorHandler.handle(error, this);
          return EMPTY;
        })
      ).subscribe(success => {
        this.router.navigate(['accounts']);
      })
    } else {
      this.serverError = 'Please check errors and click Submit again.';
    }
  }

  onCancel() {
    this.router.navigate(['accounts']);
  }

  onShippingSameChange() : void {
    let shippingAddressSameAsBilling = this.form.get('shippingAddressSameAsBilling')?.value;
    if(shippingAddressSameAsBilling) {
      this.form.patchValue({
        shippingCity: '',
        shippingCountry: '',
        shippingPostalCode: '',
        shippingProvinceState: '',
        shippingStreet: ''
      });
    }
    this.disableShipping(shippingAddressSameAsBilling == true);
  }

  private disableShipping(disable: boolean) {
    let fields = ['shippingCity', 'shippingCountry', 'shippingPostalCode', 'shippingProvinceState', 'shippingStreet'];
    fields.forEach((field) => {
      if(disable) {
        this.form.get(field)?.disable();
      } else {
        this.form.get(field)?.enable();
      }
    });
  }
}