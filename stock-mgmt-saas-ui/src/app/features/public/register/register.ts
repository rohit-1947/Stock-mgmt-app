import { Component } from '@angular/core';
import { Button } from 'primeng/button';
import { FloatLabel } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Toast } from 'primeng/toast';
import { RegisterTenantRequest } from '../../../api-services/models/register-tenant-request';
import { ValidationError } from '../../../shared/models/validation-error';
import { MessageService } from 'primeng/api';
import { Divider } from 'primeng/divider';
import { Router, RouterLink } from '@angular/router';
import { AuthenticationService } from '../../../api-services/services/authentication.service';
import { ErrorResponse } from '../../../shared/models/error-response';

@Component({
  selector: 'app-register',
  imports: [Button, FloatLabel, FormsModule, InputText, Toast, Divider, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.scss',
  providers: [MessageService],
})
export class Register {
  registerRequest: RegisterTenantRequest = {
    adminFullName: '',
    adminPassword: '',
    adminUsername: '',
    adminEmail: '',
    companyCode: '',
    companyName: '',
    email: '',
  };
  private validationErrors: Array<ValidationError> = [];

  constructor(
    private readonly authService: AuthenticationService,
    private readonly messageService: MessageService,
    private readonly router: Router,
  ) {}

  protected hasError(fieldName: string): boolean {
    return this.validationErrors.some((e) => e.field === fieldName);
  }

  protected getErrorMsg(fieldName: string): string {
    return this.validationErrors.find((e) => e.field === fieldName)?.message || '';
  }

  protected navigateToLogin() {
    this.router.navigate(['login']);
  }

  protected register() {
    this.authService
      .register({
        body: this.registerRequest,
      })
      .subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Registration successful. Please check your email for verification.',
          });
          this.router.navigate(['']);
        },
        error: (e) => {
          const error = JSON.parse(e.error as string) as ErrorResponse;
          this.validationErrors = error.validationErrors || [];
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Registration failed. Please check your input.',
          });
        },
      });
  }
}
