import { ActivatedRoute, Params, Router } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { HttpErrorHandler } from '../../../http-error-handler'
import { Note } from '../../../models/note';
import { NotesService } from '../../../services/notes.service';

import { catchError } from 'rxjs/operators'
import { of } from 'rxjs';

import { Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-note-edit',
  templateUrl: './note-edit.component.html',
  styleUrls: ['./note-edit.component.css']
})

export class NoteEditComponent implements OnInit {
  
  @Input() accountId: number;
  @Input() contactId: number;
  @Input() id: number;
  @Input() referralId: number
  @Output() closeAction = new EventEmitter<Boolean>()
  
  newMode = false;
  dialogMode = false;
  note: Note;
  serverError: String;

  form = this.formBuilder.group({
    text: ['', [Validators.required]]
  });
  
  constructor(private notesService: NotesService,
              private formBuilder: FormBuilder,
              private httpErrorHandler: HttpErrorHandler,
              private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        if(isNaN(this.id)) {
          this.id = params['id'];
          this.dialogMode = false;
        } else {
          this.dialogMode = true;
        }
        this.newMode = this.id == null || this.id == -1 || isNaN(this.id);
        this.initForm();
      }
    );
  }

  onSubmit() {
    this.serverError = '';
    var added = true;
    if(this.form.valid) {
      let saveNote = {... this.form.value, accountId: this.accountId, id: this.id, referralId: this.referralId, contactId: this.contactId};
      if(this.id == 0 || this.id == -1) {
        this.notesService.add(saveNote).pipe(
          catchError(error => {
            this.httpErrorHandler.handle(error, this);
            added = false;
            return of([]);
          })
        ).subscribe(success => {
          if(added) {
            if(this.dialogMode) {
              this.closeAction.emit(true)
            } else {
              //this.router.navigate(['tasks'])
            }
          }
        })
      } 
      
      // Disable note editing
      // else {
      //   this.notesService.change(saveNote).pipe(
      //     catchError(error => {
      //       this.httpErrorHandler.handle(error, this);
      //       added = false;
      //       return of([]);
      //     })
      //   ).subscribe(success => {
      //     if(added) {
      //       if(this.dialogMode) {
      //         this.closeAction.emit(true)
      //       } else {
      //         //this.router.navigate(['tasks'])
      //       }
      //     }
      //   })
      //}
    } else {
      this.serverError = 'Please check errors and click Submit again.'
    }
  }

  onCancel() {
    if(this.dialogMode) {
      this.closeAction.emit(false)  
    } else {
      //this.router.navigate(['tasks']);
    }
  }

  private initForm() {
    if (!this.newMode) {
      this.notesService.fetchOne(this.id).subscribe(response => {
        this.note = response;
        this.form.patchValue(response);
      })
    }
  }

}
