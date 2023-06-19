import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

import { Note } from '../../../models/note';
import { NotesService } from '../../../services/notes.service'
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { NoteEditDialogComponent } from '../note-edit-dialog/note-edit-dialog.component';

@Component({
  selector: 'app-note-sub-list',
  templateUrl: './note-sub-list.component.html',
  styleUrls: ['./note-sub-list.component.css']
})

export class NoteSubListComponent implements OnInit {
  
  @Input() accountId: number
  @Input() contactId: number
  @Input() referralId: number

  displayedColumns: string[] = ['note', 'userName', 'timeStamp']
  editNote: boolean
  form: FormGroup
  id: number
  notes: Note[]  

  constructor(private formBuilder: FormBuilder,
              private dialog: MatDialog,
              private notesService: NotesService) { }

  ngOnInit(): void {
    this.notesService.fetch(this.accountId, this.contactId, this.referralId).subscribe(notes => this.notes = notes)
    this.form = this.formBuilder.group({
      // TODO validator
      note: [],
    })
  }

  saveNote(): void {
    let note = {} as Note
    note.accountId = this.accountId
    note.contactId = this.contactId
    note.referralId = this.referralId
    note.id = this.id
    note.text = this.form.get('note')?.value
    // TODO HANDLE ERRORS HERE
    // TODO consider basing service on non-zero value of ID and only have a POST method
    if(typeof note.id == 'undefined' || note.id == 0) {
      this.notesService.add(note).subscribe(ret => {
        this.notesService.fetch(this.accountId, this.contactId, this.referralId).subscribe(notes => this.notes = notes);this.cancelNote()
      });
    } 
    
    // Disable editing of notes
    // else {
    //   this.notesService.change(note).subscribe(ret => {
    //     this.notesService.fetch(this.accountId, this.contactId, this.referralId).subscribe(notes => this.notes = notes);this.cancelNote()
    //     this.form.get('note')?.setValue('');
    //     this.id = 0;
    //   });
    // }
  }

  onNoteFocus(): void {
    this.editNote = true
  }

  cancelNote(): void {
    this.editNote = false
    this.form.get('note')?.setValue('')
  }

  // Disable deleting
  // TODO: clean this up to make it a smaller footprint, used in many places
  // openDialog(enterAnimationDuration: string, exitAnimationDuration: string, id: number): void {
  //   const dialogConfig = new MatDialogConfig();
  //   dialogConfig.disableClose = true;
  //   dialogConfig.autoFocus = true;
  //   dialogConfig.data = { description: "Delete Note?" };
  //   const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
  //   dialogRef.afterClosed().subscribe(
  //     confirmed => {
  //       if(confirmed) {
  //         this.notesService.delete(id).subscribe(success => {
  //           if(success) {
  //             // plan A was to remove just-deleted item from local array but I couldn't
  //             // get view to repaint properly even with ChangeDetectorRef, etc. So do a 
  //             // full reload --- works, but user loses their scroll, which blows donkey balls.
  //             this.notesService.fetch(this.accountId, this.contactId, this.referralId).subscribe(notes => this.notes = notes);this.cancelNote()
  //           } else {
  //             // TODO report errors properly
  //             alert('ERROR deleting Note');
  //           }
  //         });
  //       }
  //     }
  //   );    
  // }

  // TODO dont really need note ID, do we?
  edit(id: number) {
    this.id = 0;
    let text = this.notes.find(note => note.id == id)?.text;
    if(text) {
      this.form.get('note')?.setValue(text);
      this.id = id;
      this.editNote = true;
    }
  }

  // TODO REMOVE EVENT since not relevant (Note only)
  addView(mode: string, event: any, id: number) : void {
    event.stopPropagation();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {};
    dialogConfig.data.accountId = this.accountId;
    dialogConfig.data.contactId = this.contactId;
    dialogConfig.data.id = id; // -1 for ADD
    dialogConfig.data.referralId = this.referralId;
    let dialogRef = this.dialog.open(NoteEditDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          // TODO share with ngInit
          this.notesService.fetch(this.accountId, this.contactId, this.referralId).subscribe(notes => this.notes = notes)
        }
      }
    );    
  }

}