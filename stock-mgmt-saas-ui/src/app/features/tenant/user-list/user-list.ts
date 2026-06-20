import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Button } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { Toast } from 'primeng/toast';
import { Tooltip } from 'primeng/tooltip';
import { ProductResponse } from '../../../api-services/models/product-response';
import { PageResponseProductResponse } from '../../../api-services/models/page-response-product-response';
import { ProductService } from '../../../api-services/services/product.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { UserResponse } from '../../../api-services/models/user-response';
import { PageResponseUserResponse } from '../../../api-services/models/page-response-user-response';
import { UserService } from '../../../api-services/services/user.service';

@Component({
  selector: 'app-user-list',
  imports: [Button, TableModule, Toast, Tooltip],
  templateUrl: './user-list.html',
  styleUrl: './user-list.scss',
  providers: [MessageService],
})
export class UserList implements OnInit {

  protected users: UserResponse[] = [];
  private userPage: PageResponseUserResponse = {};

  constructor(
    private readonly userService: UserService,
    private readonly cd: ChangeDetectorRef,
    private readonly messageService: MessageService,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  private loadUsers() {
    this.userService
      .getAllUsers({
        page: 0,
        size: 10,
      })
      .subscribe({
        next: (res) => {
          this.userPage = res;
          this.users = [...(res.content || [])];
          this.cd.detectChanges();
        },
        error: (e) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Failed to load users.',
          });
        },
      });
  }

  protected updateUser(id: string) {
    this.router.navigate(['app', 'manage-user', id]);
  }

  protected deleteProduct(id: string) {}

  protected addUser() {
    this.router.navigate(['app', 'manage-user']);
  }
}
