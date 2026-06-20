import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-statistics',
  imports: [RouterOutlet],
  templateUrl: './statistics.html',
  styleUrl: './statistics.scss',
})
export class Statistics {

  constructor(
    private readonly router: Router,
  ) { }

  protected navigateTo(c: string) {
    if (c === 'p') {
      this.router.navigate(['app', 'products']);
    } else if (c === 'c') {
      this.router.navigate(['app', 'categories']);
    } else if (c === 's') {
      this.router.navigate(['app', 'stock-mvts']);
    } else if (c === 'u') {
      this.router.navigate(['app', 'users-list']);
    }
  }
}
