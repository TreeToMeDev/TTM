import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ProductWarrantiesMap } from '../../../enums/product-warranty.enum';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  isLoading:boolean = false;
  products: Product[];
  filterDate: Date;
  displayedColumns: string[] = ['productCode', 'description', 'quantityOnHand', 'availableDate', 'warrantyDuration'];
  dataSource = new MatTableDataSource<Product>();
  filterAvailableDate: Date;
  
  productWarranties = ProductWarrantiesMap;

  filterForm: FormGroup;
  //dataSource = new MatTableDataSource<Product>();

  // Make a variable reference to our Enum

  constructor(private productService: ProductService,
              private formBuilder: FormBuilder) {}
  
  
  ngOnInit(): void {
    this.isLoading = true;
	  this.productService.fetchAll().subscribe(products => {
        this.products = products;
        this.dataSource.data = products;
        this.setFilterForm();
        this.isLoading = false;
    });
  }

  public setFilterForm() {
    this.filterForm = this.formBuilder.group({
      filterProductCode: new FormControl(null),
      filterFromDate: new FormControl(null),
      filterToDate: new FormControl(null),
      filterWarrantyDuration: new FormControl(null)
    });
  }

  applyFilter() {
    this.dataSource.data = this.products;

    if (this.filterForm.value.filterProductCode) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        return e.productCode.toLowerCase().includes(this.filterForm.value.filterProductCode.toLowerCase());
      });
    }

    if (this.filterForm.value.filterFromDate != null) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        return e.availableDate >= this.filterForm.value.filterFromDate;
      });  
    }

    if (this.filterForm.value.filterToDate != null) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        return e.availableDate <= this.filterForm.value.filterToDate;
      });  
    }

    if (this.filterForm.value.filterWarrantyDuration) {
      this.dataSource.data = this.dataSource.data.filter(e => {
        return this.filterForm.value.filterWarrantyDuration.includes(e.warrantyDuration);
      });  
    }

  }

  resetFilterForm() {
    this.filterForm.reset();
    this.applyFilter();
  }
}
