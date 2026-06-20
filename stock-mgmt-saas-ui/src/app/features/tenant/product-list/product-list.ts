import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Button } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { Toast } from 'primeng/toast';
import { Tooltip } from 'primeng/tooltip';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { ProductResponse } from '../../../api-services/models/product-response';
import { PageResponseProductResponse } from '../../../api-services/models/page-response-product-response';
import { ProductService } from '../../../api-services/services/product.service';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-product-list',
  imports: [Button, TableModule, Toast, Tooltip, CurrencyPipe],
  templateUrl: './product-list.html',
  styleUrl: './product-list.scss',
  providers: [MessageService],
})
export class ProductList implements OnInit {
  protected products: ProductResponse[] = [];
  private productPage: PageResponseProductResponse = {};

  constructor(
    private readonly productService: ProductService,
    private readonly cd: ChangeDetectorRef,
    private readonly messageService: MessageService,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts() {
    this.productService
      .findAllProducts({
        page: 0,
        size: 10,
      })
      .subscribe({
        next: (res) => {
          this.productPage = res;
          this.products = [...(res.content || [])];
          this.cd.detectChanges();
        },
        error: (e) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Failed to load products.',
          });
        },
      });
  }

  protected updateProduct(id: string) {
    this.router.navigate(['app', 'manage-product', id]);
  }

  protected deleteProduct(id: string) {}

  protected addProduct() {
    this.router.navigate(['app', 'manage-product']);
  }
}
