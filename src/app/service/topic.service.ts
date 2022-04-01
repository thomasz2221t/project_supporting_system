import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Topic } from '../model/topic';
import { enviroment } from '../enviroment';
@Injectable({
  providedIn: 'root',
})
export class TopicService {
  apiUrl: string = enviroment.baseUrl;
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  constructor(private http: HttpClient) {}

  //create
  createTopic(topic: Topic): Observable<any> {
    const API_URL = `${this.apiUrl}/topic`;
    return this.http.post(API_URL, topic).pipe(catchError(this.error));
  }

  error(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }
}
