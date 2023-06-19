import { ActivatedRoute } from '@angular/router';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

import { Contact } from '../../../models/contact';
import { ContactService } from '../../../services/contact.service';
import { ContactEditDialogComponent } from '../contact-edit-dialog/contact-edit-dialog.component';


@Component({
  selector: 'app-contact-sub-list',
  templateUrl: './contact-sub-list.component.html',
  styleUrls: ['./contact-sub-list.component.css']
})

export class ContactSubListComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, 
              private dialog: MatDialog,
              private contactService: ContactService) {}

  @Input() accountId: number

  showTable: boolean = true;

  displayedColumns: string[] = ['name', 'email', 'phone']

  contacts: Contact[];

  ngOnInit(): void {
	  this.activatedRoute.params.subscribe(params => {
	    this.showTable = true;
      this.load();
    });
  }
  
  add(): void {
    this.contactService.setActiveRecord({} as Contact);
  }

  change(contact: Contact): void {
	  this.contactService.setActiveRecord(contact);
  }

  delete(contact: Contact): void {
	  this.contactService.setActiveRecord(contact);
  }

  load() {
    this.contactService.fetchContactsByAccount(this.accountId).subscribe(contacts => this.contacts = contacts);
  }

  addView(mode: string, event: any, id: number) : void {
    event.stopPropagation();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      accountId: this.accountId,
      id: id, // will be -1 for add
    };
    let dialogRef;
    if(mode === 'ADD') {
      dialogRef = this.dialog.open(ContactEditDialogComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        confirmed => {
          if(confirmed) {
            this.load();
          }
        }
      )
    };    
  }

}