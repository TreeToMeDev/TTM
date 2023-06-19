import { ActivatedRoute, Params, Router } from '@angular/router';
import { catchError } from 'rxjs/operators'
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EMPTY } from 'rxjs';

import { Agent } from '../../../models/agent';
import { AgentService } from '../../../services/agent.service';
import { HttpErrorHandler } from '../../../http-error-handler'

// TODO: share publicly
enum Mode {
  ADD, DUPLICATE, EDIT
}

@Component({
  selector: 'app-agent-edit',
  templateUrl: './agent-edit.component.html',
  styleUrls: ['./agent-edit.component.css']
})

export class AgentEditComponent implements OnInit {

  agentList: Agent[];
  id: number;
  formLoaded = false;
  header = '';
  mode: Mode;
  serverError: String
  showClose = false;
  agent: Agent

  form = this.formBuilder.group({
    email: [],
    firstName: [''],
    lastName: [''],
    parentId: ['', [Validators.required]]
  });

  constructor(private agentService: AgentService,
              private formBuilder: FormBuilder, 
              private route: ActivatedRoute, 
              private router: Router,
              private httpErrorHandler: HttpErrorHandler) { }

  ngOnInit(): void {
    console.log('didit');
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        if(params['id'] != null) {
          this.mode = Mode.EDIT;
          this.agentService.fetch(this.id).subscribe(response => {
            this.form.patchValue(response);
            this.header = Mode[this.mode] + ' Agent - ' + response.firstName + ' ' + response.lastName;
            // TODO should be ForkJoin maybe
            this.agentService.fetchAll().subscribe(response => {
              this.agentList = response;
              let noneParent =  {} as Agent;
              noneParent.id = 0;
              noneParent.lastName = 'None';
              this.agentList.unshift(noneParent);
            })
          })
        } else {
          this.mode = Mode.ADD;
          this.header = Mode[this.mode] + ' Agent';
          this.agentService.fetchAll().subscribe(response => {
            this.agentList = response;
            let noneParent =  {} as Agent;
            noneParent.id = 0;
            noneParent.lastName = 'None';
            this.agentList.unshift(noneParent);
          })
        }
      }
    );
  }

  onSubmit() {
    this.serverError = '';
    this.showClose = false
    if(this.form.valid) {
      this.agentService.save(this.form.value, this.id).pipe(
        catchError(error => {
          this.httpErrorHandler.handle(error, this);
          return EMPTY;
        })
      ).subscribe(success => {
        if(success && success['password']) {
          this.serverError = 'User added, generated password is ' + success['password']
        } else if(success && success['message']) {
          this.serverError = success['message']
        } else {
          this.serverError = 'User updated.';
        }
        this.showClose = true
      })
    } else {
      this.serverError = 'Please check errors and click Save again.'
    }
  }

  onCancel() {
    this.router.navigate(['agents']);
  }

}