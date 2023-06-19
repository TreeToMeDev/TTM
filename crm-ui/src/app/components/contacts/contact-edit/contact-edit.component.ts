import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Account } from '../../../models/account';
import { Agent } from '../../../models/agent';
import { AgentService } from '../../../services/agent.service';
import { Contact } from '../../../models/contact';
import { AccountsService } from '../../../services/accounts.service'
import { ContactService } from '../../../services/contact.service';
import { HttpErrorHandler } from '../../../http-error-handler'
import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';
import { SessionService } from '../../../services/session.service'

import { catchError, first } from 'rxjs/operators'
import { EMPTY, forkJoin } from 'rxjs';
import { Output, EventEmitter } from '@angular/core';
import { ContactTypeListService } from '../../../services/contact-type-list.service';
import { ContactTypeListItem } from '../../../models/contact-type-list-item';
import { ContactType } from '../../../models/contact-type';

// TODO: share publicly
enum Mode {
  ADD, DUPLICATE, EDIT
}

@Component({
  selector: 'app-contact-edit',
  templateUrl: './contact-edit.component.html',
  styleUrls: ['./contact-edit.component.css']
})
export class ContactEditComponent implements OnInit {

  @Input() accountId;
  @Output() closeAction = new EventEmitter<Boolean>();

  accountList: Account[];
  agentList: Agent[];
  contact: Contact;
  dialogMode = false;
  editMode = false; // TODO: REMOVE
  formLoaded = false;
  header = '';
  id: number;
  mode: Mode;
  serverError: String;
  userList: User[];
  contactTypeList: ContactTypeListItem[];

  form = this.formBuilder.group({
    accountId: ['', [Validators.required]],
    agentId: ['', [Validators.required]],
    city: [''],
    country: [''],
    email: ['', [Validators.required, Validators.pattern("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")]],
    firstName: [''],
    lastName: ['', [Validators.required]],
    phone: [''],
    phoneCell: [''],
    postalCode: [''],
    provinceState: [''],
    street: [''],
    title: [''],
    contactTypes: []
  });
 
  constructor(private accountsService: AccountsService,
              private agentService: AgentService,
              private contactService: ContactService,
              private formBuilder: FormBuilder, 
              private httpErrorHandler: HttpErrorHandler,
              private route: ActivatedRoute, 
              private router: Router,
              private sessionService: SessionService,
              private userService: UserService,
              private contactTypeListService: ContactTypeListService) { }

  ngOnInit(): void {
    this.dialogMode = true;
    this.mode = Mode.ADD;
    if(this.router.url.endsWith('duplicate')) {
      this.mode = Mode.DUPLICATE;
      this.dialogMode = false;
    }
    if(this.router.url.endsWith('edit')) {
      this.mode = Mode.EDIT
      this.dialogMode = false;
    }
    if(this.router.url.endsWith('new')) {
      this.dialogMode = false;
    }
    this.header = Mode[this.mode] + ' Contact';
    let accountsObservable = this.accountsService.fetchAll().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error,this);
        return EMPTY;
      })
    )
    let agentsObservable = this.agentService.fetchAll().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error,this)
        return EMPTY;
      })
    );
    let contactTypeListObservable = this.contactTypeListService.fetchAll().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error,this)
        return EMPTY;
      })
    );
    forkJoin([this.route.params.pipe(first()), accountsObservable, agentsObservable, contactTypeListObservable]).subscribe(results => {
      this.id = results[0]['id'];
      this.accountList = results[1];
      let noneAccount =  {} as Account;
      noneAccount.id = -1;
      noneAccount.name = 'None';
      this.accountList.unshift(noneAccount);
      if (this.mode == Mode.EDIT || this.mode == Mode.DUPLICATE) {
        this.contactService.fetchContact(this.id).subscribe(response => {
          this.form.patchValue(response);
          this.header += ' - ' + this.form.get('firstName')?.value + ' ' + this.form.get('lastName')?.value;
          const contactTypes: string[] = response.contactTypes.map(c => c.contactTypeKey);
          this.form.patchValue({
            contactTypes: contactTypes
          })
        })
      } else {
        this.form.patchValue({
          accountId: this.accountId
        })
        var userId = this.sessionService.getUserId();
        this.form.get('agentId')?.setValue(userId);
      }
      this.agentList = this.agentService.buildList(results[2], 0, 0);
      this.contactTypeList = results[3];
    });
  }
  
  onSubmit() {
    this.serverError = '';
    if(this.mode == Mode.DUPLICATE) {
      this.id = -1;
    }
    if(this.form.valid) {
      this.contactService.save(this.form.value, this.id).pipe(
        catchError(error => {
          this.httpErrorHandler.handle(error, this);
          return EMPTY;
        })
      ).subscribe(success => {
        if(this.dialogMode) {
          this.closeAction.emit(true)
        } else {
          this.router.navigate(['contacts'])
        }
    })
    } else {
      this.serverError = 'Please check errors and click Submit again.'
    }
  }

  onCancel() {
    if(this.dialogMode) {
      this.closeAction.emit(false)
    } else {
      this.router.navigate(['contacts']);
    }
  }
}
