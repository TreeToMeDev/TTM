import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ContactSourceMap } from '../../../enums/contact-source.enum';
import { Contact } from '../../../models/contact';
import { ContactService } from '../../../services/contact.service';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-contact-detail',
  templateUrl: './contact-detail.component.html',
  styleUrls: ['./contact-detail.component.css']
})
export class ContactDetailComponent implements OnInit {

  contact: Contact;
  id: number;

  contactSources = ContactSourceMap;

  contactTypesList: string;

  constructor(private contactService: ContactService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.params.subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.contactService.fetchContact(this.id).subscribe(response => {
            this.contact = response;
            this.contactTypesList = this.contact.contactTypes.map(c => c.contactTypeKey).join(', ');
          })
        }
    );
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Contact?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.contactService.delete(this.id).subscribe(success => {
            if(success) {
              this.router.navigate(['contacts'])
            }
          });
        }
      }
    );    
  }
}
