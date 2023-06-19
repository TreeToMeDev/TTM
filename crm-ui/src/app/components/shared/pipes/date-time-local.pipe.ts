import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
    name: 'dateTimeLocalPipe',
})
export class DateTimeLocalPipe implements PipeTransform {
    transform(value: Date) {
        var stringDate = "";

        if (value != null) {
            var pipeDate = new DatePipe('en-US');
            stringDate = pipeDate.transform(value, 'M/dd/YYYY HH:mm:ss');
        }

        return stringDate;
    }
}