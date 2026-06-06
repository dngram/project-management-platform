import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthService }
from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class DashboardComponent {

  username =
    localStorage.getItem('username');

  role =
    localStorage.getItem('role');

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  logout(): void {

    this.authService.logout();

    this.router.navigate([
      '/login'
    ]);
  }
}