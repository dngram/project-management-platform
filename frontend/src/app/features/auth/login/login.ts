import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class LoginComponent {

  email = '';

  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  login(): void {

    this.authService.login({
      email: this.email,
      password: this.password
    })
    .subscribe({

      next: response => {

        this.authService
              .saveLogin(response);

        this.router.navigate([
          '/dashboard'
        ]);
      },

      error: error => {

        alert(
          'Invalid email or password'
        );

        console.error(error);
      }
    });
  }
}