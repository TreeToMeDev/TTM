import { ErrorStateMatcher} from '@angular/material/core';
import { FormControl, FormGroupDirective, NgForm} from '@angular/forms';

export class CustomErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
      const isSubmitted = form && form.submitted;
      // GT - standard behaviour is to show errors as soon as they touch a control. That means as they type, they
      // get an error until they enter the required number of characters. This is horrible IMO. Change this to not show
      // anything until they submit. There is no way to clear the submit flag, though, so, once they submit once,
      // they will see the errors as they type. Which sucks.
      //return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
      return !!(control && control.invalid && (isSubmitted));
    }
}