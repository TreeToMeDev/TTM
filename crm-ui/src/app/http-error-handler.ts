import { HttpErrorResponse } from "@angular/common/http";

export class HttpErrorHandler {
    handle(error: HttpErrorResponse, caller: any) {
        let genericErrorMessage: String = 'An unexpected error occurred while sending this request.' 
        if(error.status != 500) {
            caller['serverError'] = genericErrorMessage
        }
        if(error.error) {
            const errors: Array<Object> = Object.keys(error.error).map(key => {
                return { text: key, value: error.error[key] };
            });
            errors.forEach( (error) => {
                var control = caller['form'].get(error['text'])
                if(control) {
                  control.setErrors({text: error['value']});
                  caller['serverError'] = 'Please correct errors and click Submit again.'
                } else {
                    caller['serverError'] = error['value'] || genericErrorMessage;
                }
            })
        } else {
            caller['serverError'] = error['value'] || genericErrorMessage;
        }
    }
}