// https://stackoverflow.com/questions/50722368/limit-input-field-to-two-decimal-places-angular-5

import { Directive, ElementRef, HostListener, Input } from '@angular/core';

import { NumberPattern } from '../../../enums/number-patterns.enum';

@Directive({
  selector: '[appInputNumber]'
})

// TODO RENAME AS DIRECTIVE, ITS NOT A COMPONENT
export class InputNumberDirective {

  @Input() appInputNumber: NumberPattern;
  
  elem: ElementRef;

  private regex: RegExp;
  private specialKeys: Array<string> = ['Backspace', 'Tab', 'End', 'Home', '-', 'ArrowLeft', 'ArrowRight', 'Del', 'Delete'];

  constructor(private el: ElementRef) {
    this.elem = el;
  }

  ngOnInit() {
    // this logic must be in ngOnInit, not constructor, because @Input() is not set until after constructor runs
    switch(this.appInputNumber) {
      case NumberPattern.NoDecimals: {
        this.regex = new RegExp(/^\d*$/g);
        break;  
      }
      case NumberPattern.TwoDecimals: {
        this.regex = new RegExp(/^\d*\.?\d{0,2}$/g);
        break;  
      }
    }
  }

  @HostListener('keydown', ['$event'])
  onKeyDown(event: KeyboardEvent) {
    // Allow Backspace, tab, end, and home keys
    if (this.specialKeys.indexOf(event.key) !== -1) {
      return;
    }
    let current: string = this.el.nativeElement.value;
    const position = this.el.nativeElement.selectionStart;
    const next: string = [current.slice(0, position), event.key == 'Decimal' ? '.' : event.key, current.slice(position)].join('');
    if (next && !String(next).match(this.regex)) {
      event.preventDefault();
    }
  }

}