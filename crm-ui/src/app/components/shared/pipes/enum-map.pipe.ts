import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name: 'enumMap',
})
export class EnumMapPipe implements PipeTransform {
    transform(value: string, arg1: any): string {
        
        //  No value?  Return blank
        if (value == null || value.trim() === "") {
            return "";
        }

        // Lookup enum based on value
        const lookup = arg1.find(k => k.name === value);
        
        //  Enum cannot be found.  Possibly because value is blank.
        //  Return blank
        if (lookup === undefined) {
            return "";
        }

        //  Return the Enum value
        return lookup.value;
    }
}