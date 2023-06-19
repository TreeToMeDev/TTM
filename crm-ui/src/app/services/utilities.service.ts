import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class UtilitiesService {

  constructor() { }

  dateToString(dt: Date): string {
	let d: Date = new Date(dt);
	var ret = 
	  (d.getFullYear()) + "-" +
	  ("0"+(d.getMonth()+1)).slice(-2) + "-" +
	  ("0" + d.getDate()).slice(-2);
	return ret;
  }
  
  stringToDate(inp: string): Date {
	if(!inp) {
	  '';
	}
	let parts = inp.match(/(\d+)/g);
	if(parts && parts.length === 3) {
	  let y = parseInt(parts[0]);
	  let m = parseInt(parts[1]) - 1; // months are 0-based1
	  let d = parseInt(parts[2]);
	  // TODO: this will accept eg. '2021-02-34', I believe it will count this as to be 5 days into March
	  // it should actually reject but there seems to be no clean way do allow text input of date in JS
	  // alternative could be to pass to Java as String rather than Date as Java has clean date validators
      let ret = new Date(y, m, d);
	  return ret;
	}
	return new Date(0, 0, 0);
  }
  
  deepCopy(inp: any): any {
	return JSON.parse(JSON.stringify(inp));
  }

}
