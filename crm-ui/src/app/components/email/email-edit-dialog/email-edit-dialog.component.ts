import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-email-edit-dialog',
  templateUrl: './email-edit-dialog.component.html',
  styleUrls: ['./email-edit-dialog.component.css']
})
export class EmailEditDialogComponent implements OnInit {

  @Input() recipientAddress: string;

  constructor() { }

  ngOnInit(): void {
    console.log('RA DC:' + this.recipientAddress);
  }

}
