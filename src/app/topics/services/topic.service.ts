import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Topic } from '../model/topic';
import { environment } from '../../../environments/environment';
import { DialogService } from '../../shared/services/dialog.service';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  API_URL = `${environment.baseUrl}/topic`;
  headers: HttpHeaders;
  httpOptions: {
    headers: HttpHeaders;
  };

  constructor(
    private http: HttpClient,
    private dialogService: DialogService,
    private oauthService: OAuthService
  ) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + this.oauthService.getAccessToken(),
    });
    this.httpOptions = {
      headers: this.headers,
    };
  }

  checkToken() {
    console.log(this.oauthService.hasValidAccessToken());
    console.log(this.oauthService.hasValidIdToken());
    if (!this.oauthService.hasValidAccessToken()) {
      this.oauthService.initCodeFlow();
    }
  }

  //create
  createTopic(topic: Topic): Observable<any> {
    this.checkToken();
    if (topic.topicName === null || topic.description === null)
      return throwError(() => {
        return 'Topic properites cannot be assigned to null';
      });
    return this.http
      .post(this.API_URL, topic, this.httpOptions)
      .pipe(catchError(this.error));
  }

  //get
  getAllTopics(page: number): Observable<Topic[]> {
    this.checkToken();
    const params = new HttpParams({});
    params.set('page', page);
    params.set('limit', page * 100);
    return this.http.get(this.API_URL, this.httpOptions).pipe(
      map((result) => result['results']),
      catchError(this.error.bind(this))
    );
  }

  //delete
  deleteTopic(id: number | string): Observable<any> {
    return this.http
      .delete(`${this.API_URL}/${id}`, this.httpOptions)
      .pipe(catchError(this.error));
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
