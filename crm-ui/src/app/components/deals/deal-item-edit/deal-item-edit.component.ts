import { ActivatedRoute } from '@angular/router';
import { catchError } from 'rxjs/operators'
import { Component, Inject, Input, OnInit } from '@angular/core';
import { first, forkJoin, of } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { Account } from '../../../models/account';
import { DealItemService } from '../../../services/deal-item.service';
import { HttpErrorHandler } from '../../../http-error-handler';
import { NumberPattern } from '../../../enums/number-patterns.enum';
import { TaskPrioritiesMap } from '../../../enums/task-priorities.enum';
import { TaskStatusesMap } from '../../../enums/task-statuses.enum';
import { User } from '../../../models/user';

// TODO make sure works with old container
import { Output, EventEmitter } from '@angular/core';
import { DealItem } from '../../../models/deal-item';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-deal-itemedit',
  templateUrl: './deal-item-edit.component.html',
  styleUrls: ['./deal-item-edit.component.css']
})

export class DealItemEditComponent implements OnInit {
  
  @Input() accountId: number;
  @Input() contactId: number;
  @Input() dealId: number;
  @Input() id: number;
  @Output() closeAction = new EventEmitter<Boolean>();
  
  numberPattern = NumberPattern;

  tno = NumberPattern.NoDecimals;
  ttwo = "TwoDecimals"; //NumberPattern.TwoDecimals;

  accountList: Account[];
  dealItem: DealItem;
  editMode = false;
  price: number;
  products: Product[];
  serverError: String
  taskPriorities = TaskPrioritiesMap;
  taskStatuses = TaskStatusesMap;
  userList: User[]
  
  form = this.formBuilder.group({
    amount: ['', []],
    price: ['', [Validators.required]],
    productId: ['', []], //['', [Validators.required]],
    quantity: ['', [Validators.required]],
  });

  constructor(private activatedRoute: ActivatedRoute,
              private dealItemService: DealItemService,
              private dialogRef: MatDialogRef<DealItemEditComponent>,
              private formBuilder: FormBuilder,
              private httpErrorHandler: HttpErrorHandler,
              private productService: ProductService,
              private route: ActivatedRoute, 
              @Inject(MAT_DIALOG_DATA) data) {
    this.dealId = data.dealId;
    this.id = data.id;
  };

  ngOnInit(): void {
    // DO WE NEED THIS QUERY JUNK I DONT THINK SO
    // TODO WHY THIS THERE ARE NO QUERY PARAMS!
    forkJoin([this.route.params.pipe(first()), this.activatedRoute.queryParams.pipe(first())]).subscribe(results => {
      // TS ALERT: if you just rely on the '?' operator, and there is no query parameter, this.accountId ends up as NaN
      // and overwrites the input param value. Not sure how this works, but this hack solves the problem.
      // TODO: combination of QueryParams and Angular @params seems confusing, think about a better way so we can
      // do it the same way in both cases...
      /*if(results[1] && results[1]?.['accountId']) {
        // TS ALERT: if you omit parseInt(), the resulting this.accountId is a STRING, not a number, even though it is
        // declared as a number at the start of this program. WTF?
        this.accountId = parseInt(results[1]?.['accountId']);
        this.contactId = parseInt(results[1]?.['contactId']);
      }
      if(isNaN(this.id)) {
        this.id = results[0]['id'];
      }*/
      this.editMode = this.id != null && this.id != -1 && !isNaN(this.id);
      // TODO: load product codes, I guess
      this.loadProductDropDown();
      // TODO: only accessible as dialog ??
      if (this.editMode) {
        this.dealItemService.fetch(this.id).subscribe(response => {
          this.dealItem = response;
          this.form.patchValue(response);
        })
      } else {
        this.form.get('status')?.setValue('NEW');
      }
    });
    this.form.valueChanges.subscribe(value => {
      if(!isNaN(this.form.value.price) && !isNaN(this.form.value.quantity)) {
        let newAmount: number = +((this.form.value.price * this.form.value.quantity).toFixed(2));
        if(newAmount != this.form.value.amount) {
          this.form.patchValue({['amount']: newAmount });
        }
      }
    })
  }

  onSubmit() {
    this.serverError = '';
    if(this.form.valid) {
      this.dealItemService.save({... this.form.value, dealId: this.dealId}, this.id).subscribe(foo => {
        this.closeAction.emit();
        this.dialogRef.close();  
      });
    }
  }

  onCancel() {
    this.closeAction.emit();
    this.dialogRef.close();
  }

  // When the product is changed then retrieve the default price for
  // the product.
  onProductChange(event) {
    let productId = event.value
    if (productId != 0) {
      let product = this.products.find(p => p.id == productId);
      this.form.patchValue({
        price: product.price
      })
    }
  }

  private loadProductDropDown() {
    this.productService.fetchAll().pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error, this);
        return of([]);
      })
    ).subscribe(result => {
      this.products = result;
      if(!this.editMode) {
        // NG ALERT: if you pass a STRING to a field that expects a NUMBER, the patch
        // silently fails
        //this.form.patchValue({['productId']: this.productId});
      }
    })
  }

}
