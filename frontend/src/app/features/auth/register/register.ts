import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';

import { AuthService }
from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class RegisterComponent {

  username = '';

  email = '';

  password = '';

  role = 'MEMBER';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  register(): void {

    this.authService.register({

      username: this.username,

      email: this.email,

      password: this.password,

      role: this.role

    }).subscribe({

      next: () => {

        alert('Registration Successful');

        console.log('Navigating to login');

        this.router.navigate(['/login'])
          .then(result =>
            console.log('Navigation result:', result)
          )
          .catch(error =>
            console.error('Navigation error:', error)
          );
      },

      error: err => {

        console.error(err);

        alert(
          err.error
        );
      }
    });
  }
}