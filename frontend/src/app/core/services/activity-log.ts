import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment }
from '../../../environments/environment';

import { ActivityLogResponse }
from '../../shared/models/activity-log-response';

@Injectable({
  providedIn: 'root'
})
export class ActivityLogService {

  constructor(
    private http: HttpClient
  ) {}

  getTaskActivity(
    taskId: number
  ): Observable<ActivityLogResponse[]> {

    return this.http.get<ActivityLogResponse[]>(
      `${environment.apiUrl}/tasks/${taskId}/activity`
    );
  }
}