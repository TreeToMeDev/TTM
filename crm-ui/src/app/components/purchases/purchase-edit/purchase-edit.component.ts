import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { EMPTY, forkJoin } from 'rxjs';
import { EnumStatic } from '../../../core/static/enum-static';
import { FormBuilder  } from '@angular/forms'
import { ProductWarranty } from '../../../enums/product-warranty.enum';

import { Product } from '../../../models/product';
import { Purchase } from '../../../models/purchase';
import { ProductService } from '../../../services/product.service';
import { PurchaseService } from '../../../services/purchase.service';

@Component({
  selector: 'app-purchase-edit',
  templateUrl: './purchase-edit.component.html',
  styleUrls: ['./purchase-edit.component.css']
})

export class PurchaseEditComponent implements OnInit {

  @Input() accountId: number
  @Input() id: number
  @Output() closeAction = new EventEmitter();

  editMode: boolean;
  isLoading: boolean = false;
  products: Product[];
  purchase: Purchase;
  
  form = this.formBuilder.group({
    productCode: [''],
    serialNo: [''],
    purchaseDate: [''],
    warrantyEndDate: ['']
  });

  constructor(private formBuilder: FormBuilder,
              private productService: ProductService,
              private purchaseService: PurchaseService) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.editMode = (this.id != null && this.id != -1);
    let productObservable = this.productService.fetchAll();
    if(this.id != -1) {
      let purchaseObservable = this.purchaseService.getOne(this.id);
      forkJoin([productObservable, purchaseObservable]).subscribe(results => { 
        this.products = results[0];
        this.purchase = results[1];
        this.accountId = this.purchase.accountId;
        this.form.patchValue(this.purchase);
      });
    } else {
      productObservable.subscribe(results => { 
        this.products = results;
      });
    }
    this.isLoading = false;
  }

  save(): void {
    if(this.editMode) {
      this.purchaseService.change({... this.form.value, id: this.id, accountId: this.accountId}).subscribe(resp => {
        this.closeAction.emit(true);  
      })
    } else {
      this.purchaseService.add({... this.form.value, accountId: this.accountId}).subscribe(resp => {
        this.closeAction.emit(true);
      })
    }
  }

  cancel(): void {
    this.closeAction.emit(false)  
  }

  onValueChange() {
    
    if (this.form.value.productCode && this.form.value.purchaseDate != null) {

      let product = this.products.find(p => p.productCode === this.form.value.productCode);
      var warrantyDate: Date = new Date(this.form.value.purchaseDate);

      switch(product?.warrantyDuration) {
        case  EnumStatic.EnumValue(ProductWarranty, ProductWarranty.SIX_MONTHS): {
          warrantyDate.setMonth(warrantyDate.getMonth() + 6);
          break;
        }
        case EnumStatic.EnumValue(ProductWarranty, ProductWarranty.ONE_YEAR): {
          warrantyDate.setMonth(warrantyDate.getMonth() + 12);
          break;
        }
        case EnumStatic.EnumValue(ProductWarranty, ProductWarranty.TWO_YEARS): {
          warrantyDate.setMonth(warrantyDate.getMonth() + 24);
          break;
        }
      }
      
      this.form.patchValue ({
        warrantyEndDate: new Date(warrantyDate)
      });   
    }
  }

}