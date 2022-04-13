import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Topic } from '../model/topic';
import { environment } from '../../../environments/environment';
import { DialogService } from '../../shared/services/dialog.service';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  API_URL = `${environment.baseUrl}/topic`;
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  constructor(private http: HttpClient, private dialogService: DialogService) {}

  //create
  createTopic(topic: Topic): Observable<any> {
    if (topic.topicName === null || topic.description === null)
      return throwError(() => {
        return 'Topic properites cannot be assigned to null';
      });
    return this.http.post(this.API_URL, topic).pipe(catchError(this.error));
  }

  //get
  getAllTopics(): Observable<Topic[]> {
    console.log('GET ALL TOPICS');
    return this.http
      .get(this.API_URL)
      .pipe(tap(console.log), catchError(this.error.bind(this)));
  }

  error(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
      this.dialogService.openErrorDialog(errorMessage);
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      this.dialogService.openErrorDialog(errorMessage);
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
