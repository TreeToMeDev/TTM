import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorHandler } from '../../../http-error-handler';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product.service';

import { catchError, first } from 'rxjs/operators'
import { EMPTY, forkJoin } from 'rxjs';

import { ProductWarrantiesMap } from '../../../enums/product-warranty.enum';
import { NumberPattern } from 'src/app/enums/number-patterns.enum';
import { AppConfigService } from 'src/app/services/app-config.service';
import { AppConfig } from 'src/app/models/app-config';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  serverError: String
  id: number;
  editMode = false;
  product: Product
  formLoaded = false;
  productWarranties = ProductWarrantiesMap;
  
  numberPattern = NumberPattern;

  tno = NumberPattern.NoDecimals;
  ttwo = "TwoDecimals"; //NumberPattern.TwoDecimals;
  
  measurementList: AppConfig[];
  
  form = this.formBuilder.group({
    productCode: ['',[Validators.required]],
    description: ['', [Validators.required]],
    price: [0.00],
    measurement: ['', [Validators.required]],
    measurementAmount: [0.00],
    quantityOnHand: [''],
    availableDate: [''],
    warrantyDuration: [''] 
  });


  constructor(private formBuilder: FormBuilder, 
              private route: ActivatedRoute, 
              private router: Router,
              private productService: ProductService,
              private appConfigService: AppConfigService,
              private httpErrorHandler: HttpErrorHandler) { }

  ngOnInit(): void {
    let measurementsObservable = this.appConfigService.fetchAllByCategory('PRODUCT.MEASUREMENT').pipe(
      catchError(error => {
        this.httpErrorHandler.handle(error,this);
        return EMPTY;
      })
    )

    forkJoin([this.route.params.pipe(first()), measurementsObservable]).subscribe(results => {
      this.id = results[0]['id'];
      this.measurementList = results[1];
      this.editMode = this.id != null;
      if (this.editMode) {
        this.productService.fetch(this.id).subscribe(response => {
          this.form.patchValue(response);
          // why do we do this?
          if (this.editMode) {
            this.form.controls['productCode'].disable();
          }    
        })
      }
    });
  }

  onSubmit() {
    this.serverError = '';
    if(this.form.valid) {
      // reenable otherwise it's not included in form.value
      this.form.controls['productCode'].enable();
      this.productService.save(this.form.value, this.id).pipe(
        catchError(error => {
          this.httpErrorHandler.handle(error, this);
          return EMPTY;
        })
      ).subscribe(success => {
        this.router.navigate(['products'])
      })
    } else {
      this.serverError = 'Please check errors and click Submit again.'
    }
  }

  onCancel() {
    this.router.navigate(['products']);
  }

}
