import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment }
from '../../../environments/environment';

import { TaskRequest }
from '../../shared/models/task-request';

import { TaskResponse }
from '../../shared/models/task-response';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private apiUrl =
    `${environment.apiUrl}/tasks`;

  constructor(
    private http: HttpClient
  ) {}

  getTasks():
    Observable<TaskResponse[]> {

    return this.http.get<TaskResponse[]>(
      this.apiUrl
    );
  }

  createTask(
    request: TaskRequest
  ): Observable<TaskResponse> {

    return this.http.post<TaskResponse>(
      this.apiUrl,
      request
    );
  }

  updateTask(
    id: number,
    task: any
  ) {

    return this.http.put(
      `${this.apiUrl}/${id}`,
      task
    );
  }

  deleteTask(
    id: number
  ) {

    return this.http.delete(
      `${this.apiUrl}/${id}`,
      {
        responseType: 'text'
      }
    );
  }

  assignTask(
  taskId: number,
  userId: number
) {

  return this.http.put(
    `${this.apiUrl}/${taskId}/assign/${userId}`,
    {}
  );
}

getMyTasks():
  Observable<TaskResponse[]> {

  return this.http.get<TaskResponse[]>(
    `${this.apiUrl}/my-tasks`
  );
}
}