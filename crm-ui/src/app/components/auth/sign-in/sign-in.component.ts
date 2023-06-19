import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    /*  When this page is opened, see if the user is logged in already.  If so,
        re-direct to the accounts page */
    this.authService.isAuthenticated$.subscribe({
      next: (isAuthenticated) => {
        if (isAuthenticated) {
          this.router.navigate(['/accounts']);
        }
      }
    })
  }

  public signIn() {
    /*  This will open up the Auth0 login screen for credentials */
    this.authService.loginWithRedirect();
  }
}
