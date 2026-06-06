import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TaskService }
from '../../../core/services/task';

import { ProjectService }
from '../../../core/services/project';

import { TaskResponse }
from '../../../shared/models/task-response';

import { ProjectResponse }
from '../../../shared/models/project-response';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './task-list.html',
  styleUrl: './task-list.scss'
})
export class TaskListComponent {

  tasks: TaskResponse[] = [];

  projects: ProjectResponse[] = [];

  title = '';

  description = '';

  status = 'TODO';

  projectId!: number;

  editingTaskId:
    number | null = null;

  constructor(
    private taskService: TaskService,
    private projectService: ProjectService
  ) {}

  ngOnInit(): void {

    this.loadTasks();

    this.loadProjects();
  }

  loadTasks(): void {

    this.taskService
      .getTasks()
      .subscribe({

        next: data => {

          this.tasks = data;
        },

        error: err => {

          console.error(err);
        }
      });
  }

  loadProjects(): void {

    this.projectService
      .getProjects()
      .subscribe({

        next: data => {

          this.projects = data;

          if (
            data.length > 0 &&
            !this.projectId
          ) {

            this.projectId =
              data[0].id;
          }
        }
      });
  }

  createTask(): void {

    this.taskService
      .createTask({

        title: this.title,

        description:
          this.description,

        status: this.status,

        projectId:
          this.projectId

      })
      .subscribe({

        next: () => {

          this.clearForm();

          this.loadTasks();
        },

        error: err => {

          console.error(err);
        }
      });
  }

  startEdit(
    task: TaskResponse
  ): void {

    this.editingTaskId =
      task.id;

    this.title =
      task.title;

    this.description =
      task.description;

    this.status =
      task.status;

    this.projectId =
      task.projectId;
  }

  updateTask(): void {

    if (
      this.editingTaskId === null
    ) {
      return;
    }

    this.taskService
      .updateTask(
        this.editingTaskId,
        {

          id:
            this.editingTaskId,

          title:
            this.title,

          description:
            this.description,

          status:
            this.status
        }
      )
      .subscribe({

        next: () => {

          this.clearForm();

          this.loadTasks();
        },

        error: err => {

          console.error(err);
        }
      });
  }

  deleteTask(
    id: number
  ): void {

    this.taskService
      .deleteTask(id)
      .subscribe({

        next: () => {

          this.loadTasks();
        },

        error: err => {

          console.error(err);
        }
      });
  }

  clearForm(): void {

    this.editingTaskId = null;

    this.title = '';

    this.description = '';

    this.status = 'TODO';
  }
}