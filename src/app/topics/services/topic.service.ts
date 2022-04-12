import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Topic } from '../model/topic';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  API_URL = `${environment.baseUrl}/topic`;
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  constructor(private http: HttpClient) {}

  //create
  createTopic(topic: Topic): Observable<any> {
    if (topic.topicName === null || topic.description === null)
      return throwError(() => {
        return 'Topic properites cannot be assigned to null';
      });
    return this.http.post(this.API_URL, topic).pipe(catchError(this.error));
  }

  //get
  getAllTopics(): Observable<any> {
    return this.http.get(this.API_URL).pipe(catchError(this.error));
  }

  error(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
