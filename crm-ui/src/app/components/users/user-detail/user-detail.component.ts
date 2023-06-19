import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';
import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})

export class UserDetailComponent implements OnInit {

  user: User
  id: number;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.userService.fetchUser(this.id).subscribe(response => {
        this.user = response
       })
      }
    );  
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete User?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.userService.delete(this.id).subscribe(success => {
            if(success) {
              this.router.navigate(['users'])
            }
          });
        }
      }
    );    
  }

}
