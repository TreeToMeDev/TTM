import { ActivatedRoute, Params, Router } from '@angular/router';
import { catchError } from 'rxjs/operators'
import { Component, Input, OnInit } from '@angular/core';
import { EMPTY, first, forkJoin, of } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';

import { Account } from '../../../models/account';
import { AccountsService } from '../../../services/accounts.service'
import { Agent } from '../../../models/agent';
import { AgentService } from '../../../services/agent.service';
import { Contact } from '../../../models/contact';
import { ContactService } from '../../../services/contact.service'
import { HttpErrorHandler } from '../../../http-error-handler'
import { SessionService } from '../../../services/session.service';
import { Task } from '../../../models/task';
import { TaskPrioritiesMap } from '../../../enums/task-priorities.enum';
import { TaskStatusesMap } from '../../../enums/task-statuses.enum';
import { TaskService } from '../../../services/task.service';


// TODO make sure works with old container
import { Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-task-edit',
  templateUrl: './task-edit.component.html',
  styleUrls: ['./task-edit.component.css']
})

export class TaskEditComponent implements OnInit {
  
  @Input() accountId: number;
  @Input() contactId: number;
  @Input() id: number;
  @Input() mode: string; // 'DIALOG' or undefined; should be ENUM?
  @Output() closeAction = new EventEmitter<Boolean>();
  
  accountList: Account[];
  agentList: Agent[]
  contactList: Contact[];
  editMode = false;
  serverError: String
  task: Task
  taskPriorities = TaskPrioritiesMap;
  taskStatuses = TaskStatusesMap;
  
  form = this.formBuilder.group({
    accountId: ['', [Validators.required]],
    agentId: ['', [Validators.required]],
    contactId: ['', [Validators.required]],
    description: ['', [Validators.required]],
    dueDate: ['', [Validators.required]],
    notes: [''],
    priority: ['', [Validators.required]],
    status: ['', [Validators.required]]
  });

  constructor(private accountsService: AccountsService,
              private activatedRoute: ActivatedRoute,
              private agentService: AgentService,
              private contactService: ContactService,
              private formBuilder: FormBuilder,
              private httpErrorHandler: HttpErrorHandler,
              private route: ActivatedRoute, 
              private router: Router,
              private sessionService: SessionService,
              private taskService: TaskService) {}

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
        this.taskService.fetch(this.id).subscribe(response => {
          this.task = response;
          this.form.patchValue(response);
        })
      } else {
        this.form.get('status')?.setValue('NEW');
      }
    });
  }

  onSubmit() {
    this.serverError = '';
    if(this.form.valid) {
      this.taskService.save(this.form.value, this.id).pipe(
        catchError(error => {
          this.httpErrorHandler.handle(error, this);
          return EMPTY;
        })
      ).subscribe(success => {
        if(this.mode == 'DIALOG') {
          this.closeAction.emit(true)
        } else {
          this.router.navigate(['tasks'])
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
      this.router.navigate(['tasks']);
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


}
