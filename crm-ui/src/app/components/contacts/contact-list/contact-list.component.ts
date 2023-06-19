import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Contact } from '../../../models/contact';
import { ContactService } from '../../../services/contact.service';
import { SessionService } from '../../../services/session.service';
import { EntityOwner, EntityOwnersMap } from '../../../enums/entity-owner.enum';
import { MatSort } from '@angular/material/sort';
import { CsvDownloadService } from '../../../services/csv-download.service';
import { ContactSourceKey, ContactSourceMap } from '../../../enums/contact-source.enum';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css']
})
export class ContactListComponent implements OnInit {

  @ViewChild('contactTbSort') contactTbSort = new MatSort();
  
  contacts: Contact[] = [];
  displayedColumns: string[] = ['firstName', 'email', 'phone', 'accountName', 'agentName', 'lastChangeTimestamp'];
  
  //  Need to do this for now because table combines first and last names to one
  //  field in the datasource.  Need to specify the fields indiviually for export.
  //  However, this will probably need to be modified in the future to handle custom
  //  columns so I can leave this for now.
  exportColumns: string[] = ['firstName', 'lastName', 'email', 'phone', 'accountName', 'agentName', 'lastChangeTimestamp'];
  
  isLoading: boolean = true;
  contactServiceSub: Subscription;
  dataSource = new MatTableDataSource<Contact>();
  entityOwnerFilters = EntityOwnersMap;
  contactSourceFilters = ContactSourceMap;
  filterForm: FormGroup;

  constructor(private contactService: ContactService,
              private sessionService: SessionService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private csvDownloadService: CsvDownloadService) { }

  ngOnInit(): void {
    this.loadAllContacts();
    this.setFilterForm();
  }

  ngAfterViewInit() {    
    this.dataSource.sort = this.contactTbSort;
  }

  public setFilterForm() {
    this.filterForm = this.formBuilder.group({
      filterContactName: new FormControl(null),
      filterEntityOwner: new FormControl(EntityOwner.MINE_ONLY),
      filterContactSource: new FormControl('ALL'),
      filterAddTimestamp: new FormControl(null)
    });

    this.applyFilter();
  }

  loadAllContacts() {
    this.isLoading = true;
    this.route.params.subscribe(
      (params: Params) => {
        this.contactServiceSub = this.contactService.fetchAllContacts().subscribe(contacts => {
          this.contacts = contacts;
          this.dataSource.data = contacts;
          this.isLoading = false;
          //  Now that the data is here we can apply the search filter if we have some
          //  defaults set
          this.applyFilter();
        });
      }
    );
  }

  applyFilter() {
    this.dataSource.data = this.contacts;

    if (this.filterForm.value.filterContactName) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        return e.firstName.toLowerCase().includes(this.filterForm.value.filterContactName.toLowerCase()) ||
               e.lastName.toLowerCase().includes(this.filterForm.value.filterContactName.toLowerCase());
      });
    }

    if (this.filterForm.value.filterEntityOwner) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        switch (this.filterForm.value.filterEntityOwner) {
          case EntityOwner.ALL:
            return true;
          case EntityOwner.MINE_ONLY:
            return e.agentName == this.sessionService.getUserName();
          case EntityOwner.NOT_ASSIGNED:
            return e.agentName == 'Not assigned';
        }
        return true;
      });
    }

    if (this.filterForm.value.filterContactSource) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        if (this.filterForm.value.filterContactSource == 'ALL') {
          return true;
        } else {
          return e.source == ContactSourceKey(this.filterForm.value.filterContactSource);
        }
      });
    }

    if (this.filterForm.value.filterAddTimestamp) {
      if (this.filterForm.value.filterAddTimestamp != null) {
        this.dataSource.data = this.dataSource.data.filter(e => {
          
          //  If we cannot determine a create date then omit.
          if (e.addTimestamp == null) {
            return false;
          }

          //  Only compare the date portion of the timestamp.
          return e.addTimestamp.getDate() == this.filterForm.value.filterAddTimestamp.getDate();
        });
      }
    }
  }

  resetFilterForm() {
    this.filterForm.reset({filterEntityOwner: EntityOwner.MINE_ONLY});
    this.applyFilter();
  }
  
  exportTable() {
    this.csvDownloadService.exportToCsv("syker-contact-export.csv", this.dataSource.data, this.exportColumns);
  }
}
