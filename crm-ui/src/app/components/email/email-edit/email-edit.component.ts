import { catchError } from 'rxjs/operators'
import { Component, Input, OnInit } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from "@angular/common/http";
import { Router } from '@angular/router';;

import { EmailService } from '../../../services/email.service';
import { environment } from '../../../../environments/environment';
import { OAuthReply } from '../../../models/oauth-reply';
import { OAuthService } from '../../../services/oauth.service';

@Component({
  selector: 'app-email-edit',
  templateUrl: './email-edit.component.html',
  styleUrls: ['./email-edit.component.css']
})

/*
 * SUMMARY OF HOW TO SEND AN EMAIL WITH GMAIL, UNDER SOME REAL USER'S EMAIL ACCOUNT
 *
 * This process is special because we can send email under (say) Toby's email, without knowing his
 * password. Toby will be prompted (by Google) to grant us authorization and we can use that authorization
 * to send the email.
 * 
 * If we want to send email with knowledge of a Gmail password, IT'S EASY. We can just use SMTP. But that's not
 * the case here. It would be terrible to ask Toby for his email password.
 * 
 * Anyway, here's what we do:
 * 
 * 1. Before sending email, do a GET to /oauth.
 * 2. If this returns oAuthValid, you're good to go.
 * 3. If this returns oAuthValid = false, redirect to the URL provided in the same GET response.
 * 4. User sees Google authorization and hopefully agrees.
 * 5. Google redirects back here, to this component.
 * 6. We send the redirect URL to our back end, which uses it to get an access token.
 * 7. We can use this access token to send the email.
 * 
 * Yep, it's a seven-step process. That's just how it is. It only happens when a user authorizes us for
 * the first time. After that, the token is saved in oauth_token for future use, and this token should
 * work forever (well, there is a refresh thing, but that's handled without front-end involement).
 * 
 * This process is somewhat generic and in THEORY could work with any OAuth-secured API. But it's only
 * been attempted with GOOGLE and while assumptions about GOOGLE are minimal, they are not zero.
 * 
 * This process is also dependent on our App being setup in the Google API console. This is currently
 * under GT's personal email. It will work fine forever, however this isn't really proper and should be under
 * some Syker-generic admin user email account. I guess.
 */

export class EmailEditComponent implements OnInit {

  @Input() recipientAddress: string;

  oAuthValid: boolean = false;
  oAuthGetAccessCodeUrl: string;
  url: string = environment.apiUrl + 'rest/';

  constructor(private emailService: EmailService,
              private formBuilder: FormBuilder, 
              private httpClient: HttpClient,
              private oAuthService: OAuthService,
              private router: Router) { }

  form = this.formBuilder.group({
    body: ['', [Validators.required]],
    recipient: ['', [Validators.required]]
  });
            
  ngOnInit(): void {
    console.log('RA:' + this.recipientAddress);
    /* If the URL looks like we are here due to a redirect from Google OAuth, it means the user had just invoked
      * clicked the Authorize Gmail button and then proceeded to go thru the Google authorization process. Now we have to send the authorization
      * to Google, use it to get an access token, and finally save that in our DB so we can send the email. This is all
      * handled server-side, starting here, with the access code that's buried in the URL. The URL looks like this:
      * http://$SYKER-URL/email?code=4%2F0AVHEtk7DvGJTiUymeXaGd1jURmh7L4MaYwpTYCKMxs7KoUvpG114CZVHCuD3sOUKOCcBfw&scope=https:%2F%2Fwww.googleapis.com%2Fauth%2Fgmail.compose
      *
      * This ONLY happens if the user had NOT previously authorized us to use Google.
      */
    if(this.router.url.startsWith('/email?code=')) {
      this.oAuthService.saveAccessCode(this.router.url, window.location.href).subscribe(reply => {
        // TODO: don't just assume it worked!
        // consider recalling getIsAuthorized?
        this.oAuthValid = true;
      })
      } else {
        /* Call this method prior to attempting to compose an email, it will verify that we have access to Google by checking
        * our database; if we do not, the call supplies a redirect to get authorization from Google. Google then does a redirect
        * back to us, which is handled in the ngOnInit method above.
        * 
        * This happens EVERY TIME we come into this window to access Google. But the redirect and such only happens if we did
        * not already have authorization.
        */
      this.oAuthService.getIsAuthorized(window.location.href)
        .subscribe(reply => {
          this.oAuthValid = reply.oAuthValid;
          if(!(this.oAuthValid)) {
            this.oAuthGetAccessCodeUrl = reply.oAuthGetAccessCodeUrl.replace(/FRONT_END_REDIRECT_URI/g, window.location.href);
          }
        });
      }
  }

  /* When they click to go ahead with authorization, redirect to Google who will hopefully redirect back to us with
     the token we need (if the user okays everything). This only happens the first time the user uses Gmail with Syker. */

  authorize(): void {
    window.location.href = this.oAuthGetAccessCodeUrl;
  }
  
  send(): void {
    var emailRequest = {
      body: this.form.get('body')?.value,
      recipient: this.form.get('recipient')?.value,
      sourceUrl: window.location.href,
      subject: 'Testing from Macbook'
    }
    this.emailService.send(emailRequest)
      //this.httpClient.post<OAuthReply>(this.url + 'email', emailRequest)
      .subscribe(reply => {
        // TODO handle errors
      });
  }

}