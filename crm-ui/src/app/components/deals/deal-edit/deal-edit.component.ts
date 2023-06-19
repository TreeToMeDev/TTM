import { ActivatedRoute, Params, Router } from '@angular/router';
import { catchError } from 'rxjs/operators'
import { Component, Input, OnInit } from '@angular/core';
import { EMPTY, first, forkJoin, of, Subject } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';

import { Account } from '../../../models/account';
import { AccountsService } from '../../../services/accounts.service'
import { Agent } from '../../../models/agent';
import { AgentService } from '../../../services/agent.service';
import { Contact } from '../../../models/contact';
import { ContactService } from '../../../services/contact.service'
import { Deal } from '../../../models/deal';
import { DealService } from '../../../services/deal.service';
import { DealStagesMap } from '../../../enums/deal-stages.enum';
import { HttpErrorHandler } from '../../../http-error-handler'
import { SessionService } from '../../../services/session.service';


// TODO make sure works with old container
import { Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-deal-edit',
  templateUrl: './deal-edit.component.html',
  styleUrls: ['./deal-edit.component.css']
})

export class DealEditComponent implements OnInit {
  
  @Input() accountId: number;
  @Input() contactId: number;
  @Input() id: number;
  @Input() mode: string; // 'DIALOG' or undefined; should be ENUM?
  @Output() closeAction = new EventEmitter<Boolean>();
  
  accountList: Account[];
  agentList: Agent[]
  contactList: Contact[];
  deal: Deal;
  dealStages = DealStagesMap;
  editMode = false;
  serverError: String;
  totalValue = 0;
  updatedDealId: Subject<number> = new Subject();
  
  form = this.formBuilder.group({
    accountId: ['', [Validators.required]],
    agentId: ['', [Validators.required]],
    contactId: ['', [Validators.required]],
    name: ['', [Validators.required]],
    dueDate: ['', [Validators.required]],
    stage: ['', [Validators.required]],
    totalValue: ['', []]
  });

  constructor(private accountsService: AccountsService,
              private agentService: AgentService,
              private activatedRoute: ActivatedRoute,
              private contactService: ContactService,
              private dealService: DealService,
              private formBuilder: FormBuilder,
              private httpErrorHandler: HttpErrorHandler,
              private route: ActivatedRoute, 
              private router: Router,
              private sessionService: SessionService) {}

  ngOnInit(): void {
    forkJoin([this.route.params.pipe(first()), this.activatedRoute.queryParams.pipe(first())]).subscribe(results => {
      // TS ALERT: if you just rely on the '?' operator, and there is no query parameter, this.accountId ends up as NaN
      // and overwrites the input param value. Not sure how this works, but this hack solves the problem.
      // TODO: combination of QueryParams and Angular @params seems confusing, think about a better way so we can
      // do it the same way in both cases...
      if(results[1] && results[1]?.['accountId']) {
        // TS ALERT: if you omit parseInt(), the resulting this.accountId is a STRING, not a number, even though it is
        // declared as a number at the start of this program. WTF?
        this.accountId = parseInt(results[1]?.['accountId']);
        this.contactId = parseInt(results[1]?.['contactId']);
      }
      if(isNaN(this.id)) {
        this.id = results[0]['id'];
      }
      this.editMode = this.id != null && this.id != -1 && !isNaN(this.id);
      this.loadAccountDropDown();
      this.loadAgentDropDown();
      this.loadContactDropDown();
      if (this.editMode) {
        this.dealService.fetch(this.id).subscribe(response => {
          this.deal = response;
          this.form.patchValue(response);
        })
      } else {
        this.form.get('status')?.setValue('NEW');
      }
    });
  }

  receiveMessage($event) {
    //this.form.patchValue({['totalValue']: $event});
    this.totalValue = $event;
  }

  onSubmit() {
    this.serverError = '';
    if(this.form.valid) {
      this.dealService.save(this.form.value, this.id).pipe(
        catchError(error => {
          this.httpErrorHandler.handle(error, this);
          return EMPTY;
        })
        ).subscribe(dealId => {
          // notify sublist to save itself
          this.updatedDealId.next(parseInt(dealId + ''));
          if(this.mode == 'DIALOG') {
            this.closeAction.emit(true)
          } else {
            this.router.navigate(['deals'])
          }
        })
    } else {
      this.serverError = 'Please check errors and click Submit again.'
    }
  }

  onCancel() {
    if(this.mode == 'DIALOG') {
      this.closeAction.emit(false)
    } else {
      this.router.navigate(['deals']);
    }
  }

  private loadAccountDropDown() {
    this.accountsService.fetchAll().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error, this);
        return of([]);
      })
    ).subscribe(accountList => {
      this.accountList = accountList;
      let noneAccount =  {} as Account;
      noneAccount.id = -1;
      noneAccount.name = 'None';
      this.accountList.unshift(noneAccount);
      if(!this.editMode) {
        // NG ALERT: if you pass a STRING to a field that expects a NUMBER, the patch
        // silently fails
        this.form.patchValue({['accountId']: this.accountId});
      }
    })
  }

  private loadContactDropDown() {
    this.contactService.fetchAllContacts().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error, this);
        return of([]);
      })
    ).subscribe(contactList => {
      this.contactList = contactList;
      let noneContact =  {} as Contact;
      noneContact.id = -1;
      noneContact.firstName = 'None';
      this.contactList.unshift(noneContact);
      if(!this.editMode) {
        this.form.patchValue({
          contactId: this.contactId
        })
      }
    })
  }

  private loadAgentDropDown() {
    this.agentService.fetchAll().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error, this);
        return of([]);
      })
    ).subscribe(agentList => {
      this.agentList = this.agentService.buildList(agentList, 0, 0);
      if(!this.editMode) {
        this.form.patchValue({
          agentId: this.sessionService.getUserId()
        })
      }
    })
  }

}
