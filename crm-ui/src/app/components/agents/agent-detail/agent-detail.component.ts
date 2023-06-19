import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';

import { Agent } from '../../../models/agent';
import { AgentService } from '../../../services/agent.service';

@Component({
  selector: 'app-agent-detail',
  templateUrl: './agent-detail.component.html',
  styleUrls: ['./agent-detail.component.css']
})

export class AgentDetailComponent implements OnInit {

  agent: Agent;
  id: number;

  constructor(private agentService: AgentService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.agentService.fetch(this.id).subscribe(response => {
        this.agent = response
       })
      }
    );  
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Agent?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.agentService.delete(this.id).subscribe(success => {
            if(success) {
              this.router.navigate(['agents'])
            }
          });
        }
      }
    );    
  }

}
