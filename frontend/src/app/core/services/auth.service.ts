import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';

import { LoginRequest } from '../../shared/models/login-request';
import { LoginResponse } from '../../shared/models/login-response';
import { RegisterRequest } from '../../shared/models/register-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl =
    `${environment.apiUrl}/auth`;

  constructor(
    private http: HttpClient
  ) { }

  login(
    request: LoginRequest
  ): Observable<LoginResponse> {

    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
      request
    );
  }

  saveToken(token: string): void {

    localStorage.setItem(
      'token',
      token
    );
  }

  getToken(): string | null {

    return localStorage.getItem(
      'token'
    );
  }

logout(): void {

  localStorage.clear();
}

  isLoggedIn(): boolean {

    return this.getToken() !== null;
  }

    register(request: RegisterRequest) {

    return this.http.post(
        `${this.apiUrl}/register`,
        request,
        {
        responseType: 'text'
        }
    );
    }

  saveLogin(response: LoginResponse): void {
    localStorage.setItem(
        'token',
        response.token
    );

    localStorage.setItem(
        'username',
        response.username
    );

    localStorage.setItem(
        'role',
        response.role
    );
}

forgotPassword(email: string) {

  return this.http.post(
    `${this.apiUrl}/forgot-password`,
    {
      email
    },
    {
      responseType: 'text'
    }
  );
}

resetPassword(
  token: string,
  newPassword: string
) {

  return this.http.post(
    `${this.apiUrl}/reset-password`,
    {
      token,
      newPassword
    },
    {
      responseType: 'text'
    }
  );
}

}