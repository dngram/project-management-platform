import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TaskService }
from '../../../core/services/task';

import { TaskResponse }
from '../../../shared/models/task-response';

@Component({
  selector: 'app-my-tasks',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './my-tasks.html',
  styleUrl: './my-tasks.scss'
})
export class MyTasksComponent {

  tasks: TaskResponse[] = [];

  constructor(
    private taskService: TaskService
  ) {}

  ngOnInit(): void {

    this.loadMyTasks();
  }

  loadMyTasks(): void {

    this.taskService
      .getMyTasks()
      .subscribe({

        next: data => {

          this.tasks = data;
        },

        error: err => {

          console.error(err);
        }
      });
  }
}