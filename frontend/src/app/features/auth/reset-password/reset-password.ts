import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import {
  ActivatedRoute,
  Router
} from '@angular/router';

import { AuthService }
from '../../../core/services/auth.service';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.scss'
})
export class ResetPasswordComponent {

  token = '';

  newPassword = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {

    this.token =
      this.route.snapshot
      .queryParamMap
      .get('token') || '';
  }

  submit(): void {

    this.authService
      .resetPassword(
        this.token,
        this.newPassword
      )
      .subscribe({

        next: response => {

          alert(response);

          this.router.navigate(
            ['/login']
          );
        },

        error: err => {

          alert(err.error);
        }
      });
  }
}