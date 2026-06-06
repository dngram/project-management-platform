import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectService } from '../../../core/services/project';

import { ProjectResponse } from '../../../shared/models/project-response';

import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './project-list.html',
  styleUrl: './project-list.scss'
})
export class ProjectListComponent {

  projects: ProjectResponse[] = [];
  name = '';
  description = '';
  editingProjectId: number | null = null;

  constructor(
    private projectService: ProjectService
  ) {}

  ngOnInit(): void {

    this.loadProjects();
  }

  loadProjects(): void {

    this.projectService
      .getProjects()
      .subscribe({

        next: data => {

          this.projects = data;
        },

        error: err => {

          console.error(err);
        }
      });
  }

  deleteProject(
    id: number
  ): void {

    this.projectService
      .deleteProject(id)
      .subscribe({

        next: () => {

          this.loadProjects();
        },

        error: err => {

          console.error(err);
        }
      });
  }

  createProject(): void {

  this.projectService
    .createProject({

      name: this.name,

      description: this.description

    })
    .subscribe({

      next: () => {

        this.name = '';

        this.description = '';

        this.loadProjects();
      },

      error: err => {

        console.error(err);
      }
    });
}

startEdit(project: ProjectResponse): void {

  this.editingProjectId = project.id;

  this.name = project.name;

  this.description =
    project.description;
}

updateProject(): void {

  if (
    this.editingProjectId === null
  ) {
    return;
  }

  this.projectService
    .updateProject(
      this.editingProjectId,
      {
        id: this.editingProjectId,
        name: this.name,
        description: this.description
      }
    )
    .subscribe({

      next: () => {

        this.editingProjectId = null;

        this.name = '';

        this.description = '';

        this.loadProjects();
      }
    });
}
}