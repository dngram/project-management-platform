import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment }
from '../../../environments/environment';

import { NotificationResponse }
from '../../shared/models/notification-response';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private apiUrl =
    `${environment.apiUrl}/notifications`;

  constructor(
    private http: HttpClient
  ) {}

  getNotifications():
    Observable<NotificationResponse[]> {

    return this.http.get<NotificationResponse[]>(
      this.apiUrl
    );
  }

  markAsRead(
    id: number
  ) {

    return this.http.put(
      `${this.apiUrl}/${id}/read`,
      {}
    );
  }

  markAllAsRead() {

    return this.http.put(
      `${this.apiUrl}/read-all`,
      {}
    );
  }
}