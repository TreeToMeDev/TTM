import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
    name: 'dateUTCPipe',
})
export class DateUTCPipe implements PipeTransform {
    transform(value: Date) {
       
        var stringDate = "";

        if (value != null) {
            var datePipe = new DatePipe("en-US", "UTC");
            stringDate = datePipe.transform(value, "M/dd/yyyy");
        }
        
        return stringDate;
    }
}