import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AgentService } from 'src/app/services/agent.service';
import { SessionService } from 'src/app/services/session.service';
import { Agent } from '../../models/agent';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  isLoading: boolean = false;

  loginUserParentId: number = 0;
  userName: String;

  users: Agent[] = [];
  userSelected: number;

  constructor(private sessionService: SessionService,
              private agentService: AgentService) { }

  ngOnInit(): void {
    this.isLoading = true;

    this.sessionService.getUserInfo().then(userInfo => {
      this.userName = this.sessionService.getUserName();
      this.loginUserParentId = this.sessionService.session.parentId;

      //  Have to fix this proper in the next change
      if (this.sessionService.session.parentId == -1) {
        this.agentService.fetchAll().subscribe(data => {
          this.users = data;
          this.userSelected = this.sessionService.getUserId();
          this.isLoading = false;

        })  
      } else {
        this.userSelected = this.sessionService.getUserId();
        this.isLoading = false;
      }
    })
  }

  onUserChange() {

  }
}
