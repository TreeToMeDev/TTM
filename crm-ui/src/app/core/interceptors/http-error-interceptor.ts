import { catchError } from 'rxjs/operators';
import { EMPTY, Observable, throwError } from 'rxjs';
import { HttpContextToken, HttpEvent, HttpHandler, HttpInterceptor,HttpRequest } from '@angular/common/http';
import { HttpErrorDialogComponent } from '../../components/http-error-dialog/http-error-dialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Injectable } from '@angular/core';

export const BYPASS_INTERCEPTOR = new HttpContextToken(() => false);
export const HIDE_DETAILS = new HttpContextToken(() => false);

/*
 * This class will intercept all HTTP traffic and deal with many errors generically.
 * We have two basic situations:
 * 
 * 1. CALLS THAT SHOULD NEVER FAIL
 * 
 * These include all 'get/fetch' type operations such as populating account-list-component. There is nothing the user
 * can do to screw this up - if it fails, it is for one of these reasons:
 * 
 * A. We made a mistake somewhere which the backend detected. An example would be failure to update mapper.xml which
 *    would cause the MyBatis call to blow up. The back end MUST report these as error.status 500. This is the default set in
 *    the backend GlobalExceptionHandler, so anything not specifically handled will come back thus.
 * B. There is some network-type problem, server down etc. Angular should report these as error.status == 0.
 * C. 404's, which would happen if the URL is screwed up for example, are also handled this way.
 * 
 * In either case THIS INTERCEPTOR will report the error to the user and NOT pass anything back to the client service.
 * Thus, the service DOES NOT NEED ANY ERROR HANDLING CODE.
 * 
 * 2. CALLS THAT WILL ROUTINELY FAIL
 * 
 * These include all 'add/change/delete' operations such as in account-edit-component. The back end MUST be coded to report
 * these as error.status 400 via throwing an Exception (such as ServiceValidationException). When that happens, THIS 
 * INTERCEPTOR WILL IGNORE THE ERROR and assume that the client has a 'catchError' clause to handle the problem.
 * If this client FAILS to do this, then the error will barf to the console. (It would be nice if we could know that the
 * originating client has a catchError clause, but beyond messy Context stuff, I can't think of a nice way to do this).
 * 
 * NOTE: the client service should generally NOT need a catchError clause, this can be coded only in the component,
 * when it is expected that this type of error might happen. Having a second catchError in the service will work but
 * it's not necessary.
 * 
 * NOTE: components should generally pass the error to HttpErrorHandler. HttpErrorHandler will deal with setting
 * the error messages on the form and individual fields as required. Note that HttpErrorHandler is a thing GT wrote, it's
 * not part of Angular, and it makes assumptions about how our code is structured. If you have a better idea, go for it!
 * 
 * SPECIAL CASE:
 * 
 * Maybe in some special case we want to bypass this interceptor COMPLETELY, for example, if we want to handle ALL errors
 * including 0's and 500's in some custom way in one particular service. To achieve this, add a CONTEXT object to the HTTP
 * call as follows. The interceptor will just pass the error along as if it had never existed. The service must have a 
 * catchError clause to deal with it, otherwise it will barf to the console.
 * 
 * 1. import { BYPASS_INTERCEPTOR } from '../core/interceptors/http-error-interceptor'
 * 2. return this.http.get<Account[]>(this.url, { context: new HttpContext().set(BYPASS_INTERCEPTOR, true) });
 * 
 * ONE MORE THING:
 * 
 * Suppress the details of the backend error (eg. the Java stack trace) from any call by passing HIDE_DETAILS via
 * HttpContext, following the above example with BYPASS_INTERCEPTOR. You can specify one, both, or either. You probably
 * want to specify both or neither, though.
 * 
 * TO SUM UP:
 * 
 * 1. When coding fetch/get type operations that should never cause an error, omit catchError and rely on the interceptor
 *    to let the user know that there's a problem.
 * 2. When coding add/change/delete operations that will routinely cause an error, return an HTTP 400 and use catchError 
 *    (in the component) to pass it on to HttpErrorHandler. The component must have an error message field declared and
 *    the component must follow the Material system for field-level error reporting.
 * 
 */

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

    constructor(private dialog: MatDialog) {}

    public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      return next.handle(request).pipe(
        catchError(error => {
          if(request.context.get(HIDE_DETAILS) == true) {
            // replace error with a generic one ... they can still see it in the Network console of course.
            error = new Error ('A system error occurred while sending this request.');
          }
          if(request.context.get(BYPASS_INTERCEPTOR) == true) {
            return throwError(() => error);
          }
          if(error.status == 400) {
            return throwError(() => error);
          }
          const dialogConfig = new MatDialogConfig();
          dialogConfig.disableClose = true;
          dialogConfig.autoFocus = true;
          dialogConfig.data = {...request, ...error};
          this.dialog.open(HttpErrorDialogComponent, dialogConfig);
          return EMPTY;
        })
      )
    }

}