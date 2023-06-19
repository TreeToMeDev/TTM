import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ProductWarrantiesMap } from '../../../enums/product-warranty.enum';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product.service';

import { DeleteDialogComponent } from '../../shared/dialog/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  product: Product;
  id: number;
  productWarranties = ProductWarrantiesMap;

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.productService.fetch(this.id).subscribe(response => {
          this.product = response;
        })
      }
  );
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { description: "Delete Product?" };
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      confirmed => {
        if(confirmed) {
          this.productService.delete(this.id).subscribe(success => {
            if(success) {
              this.router.navigate(['products'])
            }
          });
        }
      }
    );    
  }

}


