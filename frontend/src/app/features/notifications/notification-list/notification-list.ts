import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NotificationService }
from '../../../core/services/notification';

import { NotificationResponse }
from '../../../shared/models/notification-response';

@Component({
  selector: 'app-notification-list',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './notification-list.html',
  styleUrl: './notification-list.scss'
})
export class NotificationListComponent {

  notifications:
    NotificationResponse[] = [];

  constructor(
    private notificationService:
      NotificationService
  ) {}

  ngOnInit(): void {

    this.loadNotifications();
  }

  loadNotifications(): void {

    this.notificationService
      .getNotifications()
      .subscribe({

        next: data => {

          this.notifications = data;
        },

        error: err => {

          console.error(err);
        }
      });
  }

  markAsRead(
    id: number
  ): void {

    this.notificationService
      .markAsRead(id)
      .subscribe({

        next: () => {

          this.loadNotifications();
        }
      });
  }

  markAllAsRead(): void {

    this.notificationService
      .markAllAsRead()
      .subscribe({

        next: () => {

          this.loadNotifications();
        }
      });
  }
}