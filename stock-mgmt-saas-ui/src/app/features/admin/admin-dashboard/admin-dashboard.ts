import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Button } from 'primeng/button';
import { TokenService } from '../../../core/token/token-service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [RouterOutlet, Button],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.scss',
})
export class AdminDashboard {
  constructor(private readonly tokenService: TokenService) {}

  protected async logout() {
    await this.tokenService.logout();
  }
}
