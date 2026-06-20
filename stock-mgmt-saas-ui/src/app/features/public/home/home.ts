import { Component } from '@angular/core';
import { Button } from 'primeng/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [Button],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  constructor(
    private router: Router
  ) { }

  protected navigateToLogin() {
    this.router.navigate(['login']);
  }

  protected navigateToRegister() {
    this.router.navigate(['register']);
  }
}
