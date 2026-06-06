import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { CommentRequest }
from '../../shared/models/comment-request';

import { CommentResponse }
from '../../shared/models/comment-response';

import { environment }
from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(
    private http: HttpClient
  ) {}

  getComments(
    taskId: number
  ): Observable<CommentResponse[]> {

    return this.http.get<CommentResponse[]>(
      `${environment.apiUrl}/tasks/${taskId}/comments`
    );
  }

  addComment(
    taskId: number,
    request: CommentRequest
  ): Observable<CommentResponse> {

    return this.http.post<CommentResponse>(
      `${environment.apiUrl}/tasks/${taskId}/comments`,
      request
    );
  }
}