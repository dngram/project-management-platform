import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AuthService }
from '../../../core/services/auth.service';

import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.scss'
})
export class ForgotPasswordComponent {

  email = '';

  constructor(
    private authService: AuthService
  ) {}

  submit(): void {

    this.authService
      .forgotPassword(this.email)
      .subscribe({

        next: response => {

          alert(response);
        },

        error: err => {

          alert(err.error);
        }
      });
  }
}