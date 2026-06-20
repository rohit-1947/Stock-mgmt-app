import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Toast } from 'primeng/toast';
import { Tooltip } from 'primeng/tooltip';
import { CategoryResponse } from '../../../api-services/models/category-response';
import { PageResponseCategoryResponse } from '../../../api-services/models/page-response-category-response';
import { CategoryService } from '../../../api-services/services/category.service';
import { MessageService } from 'primeng/api';
import { Button } from 'primeng/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-list',
  imports: [TableModule, Toast, Tooltip, Button],
  templateUrl: './category-list.html',
  styleUrl: './category-list.scss',
  providers: [MessageService],
})
export class CategoryList implements OnInit {
  protected categories: CategoryResponse[] = [];
  private categoryPage: PageResponseCategoryResponse = {};

  constructor(
    private readonly categoryService: CategoryService,
    private readonly cd: ChangeDetectorRef,
    private readonly messageService: MessageService,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  private loadCategories() {
    this.categoryService
      .findAllCategories({
        page: 0,
        size: 10,
      })
      .subscribe({
        next: (res) => {
          this.categoryPage = res;
          this.categories = [...(res.content || [])];
          this.cd.detectChanges();
        },
        error: (e) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Failed to load categories.',
          });
        },
      });
  }

  protected updateCategory(id: string) {
    this.router.navigate(['app', 'manage-category', id]);
  }

  protected deleteCategory(id: string) {}

  protected addCategory() {
    this.router.navigate(['app', 'manage-category']);
  }
}
