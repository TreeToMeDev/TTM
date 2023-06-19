import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { SessionService } from '../../services/session.service';

class Module {
  label: string
  routerLink: string
  constructor(label: string, routerLink: string) {
    this.label = label
    this.routerLink = routerLink
  }
}

@Component({
  selector: 'app-syker-header',
  templateUrl: './syker-header.component.html',
  styleUrls: ['./syker-header.component.css']
})

export class SykerHeaderComponent implements OnInit {

  constructor(private authService: AuthService, 
              private sessionService: SessionService,
              @Inject(DOCUMENT) private doc: Document) { }

  modules : Module[] = []

  ngOnInit(): void {
    this.sessionService.getUserInfo().then(userInfo => {
      this.modules.push(new Module('Contacts', '/contacts'));
      this.modules.push(new Module('Accounts', '/accounts'));
      this.modules.push(new Module('Referrals', '/referrals'));
      this.modules.push(new Module('Products', '/products'));
      this.modules.push(new Module('Tasks', '/tasks'));
      this.modules.push(new Module('Deals', '/deals'))
      if(this.sessionService.getHasUsersAccess()) {
        this.modules.push(new Module('Agents', '/agents'))
        this.modules.push(new Module('Users', '/users'))
        this.modules.push(new Module('Email', '/email'))
      }
    })
  }

  logout() {
    this.authService.logout({
      returnTo: this.doc.location.origin,
    });
  }

}
