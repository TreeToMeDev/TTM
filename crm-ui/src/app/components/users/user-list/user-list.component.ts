import { Component, OnInit } from '@angular/core';

import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  constructor(private userService: UserService) { }

  users: User[]

  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'accessUsers']
  
  ngOnInit(): void { 
    this.userService.fetchAllUsers().subscribe(data => {
      this.users = data;
    })
  }

}
