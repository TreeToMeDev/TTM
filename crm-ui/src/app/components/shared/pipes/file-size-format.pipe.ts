import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
    name: 'fileSizeFormatPipe',
})
export class FileSizeFormatPipe implements PipeTransform {
    transform(value: number, decimals = 2) {
        if (value == 0) return '0 Bytes'
        const k = 1024
        const dm = decimals < 0 ? 0 : decimals
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']

        const i = Math.floor(Math.log(value) / Math.log(k))

        return `${parseFloat((value / Math.pow(k, i)).toFixed(dm))} ${sizes[i]}`
    }
}