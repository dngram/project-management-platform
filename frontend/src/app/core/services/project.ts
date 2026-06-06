import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment }
from '../../../environments/environment';

import { ProjectRequest }
from '../../shared/models/project-request';

import { ProjectResponse }
from '../../shared/models/project-response';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private apiUrl =
    `${environment.apiUrl}/projects`;

  constructor(
    private http: HttpClient
  ) {}

  getProjects():
    Observable<ProjectResponse[]> {

    return this.http.get<ProjectResponse[]>(
      this.apiUrl
    );
  }

createProject(
  request: ProjectRequest
) {

  return this.http.post<ProjectResponse>(
    this.apiUrl,
    request
  );
}

  deleteProject(
    id: number
  ) {

    return this.http.delete(
      `${this.apiUrl}/${id}`,
      {
        responseType: 'text'
      }
    );
  }

  updateProject(
  id: number,
  project: any
) {

  return this.http.put(
    `${this.apiUrl}/${id}`,
    project
  );
}
}