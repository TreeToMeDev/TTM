import { ActivatedRoute, Params, Router } from '@angular/router';
import { catchError } from 'rxjs/operators'
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EMPTY } from 'rxjs';

import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';
import { HttpErrorHandler } from '../../../http-error-handler'

// TODO: share publicly
enum Mode {
  ADD, DUPLICATE, EDIT
}

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})

export class UserEditComponent implements OnInit {

  id: number;
  formLoaded = false;
  header = '';
  mode: Mode;
  serverError: String
  showClose = false;
  user: User

  form = this.formBuilder.group({
    accessUsers: [''],
    email: [],
    firstName: [''],
    lastName: ['']
  });

  constructor(private formBuilder: FormBuilder, 
              private route: ActivatedRoute, 
              private router: Router,
              private userService: UserService,
              private httpErrorHandler: HttpErrorHandler) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        if(params['id'] != null) {
          this.mode = Mode.EDIT;
          this.userService.fetchUser(this.id).subscribe(response => {
            this.form.patchValue(response);
            this.header = Mode[this.mode] + ' User - ' + response.firstName + ' ' + response.lastName;
          })
        } else {
          this.mode = Mode.ADD;
          this.header = Mode[this.mode] + ' User';
        }
      }
    );
  }

  onSubmit() {
    this.serverError = '';
    this.showClose = false
    if(this.form.valid) {
      this.userService.save(this.form.value, this.id).pipe(
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
    this.router.navigate(['users']);
  }

}