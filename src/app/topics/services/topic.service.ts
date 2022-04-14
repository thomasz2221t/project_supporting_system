import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { noop, Observable, throwError, EMPTY } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Topic } from '../model/topic';
import { environment } from '../../../environments/environment';
import { DialogService } from '../../shared/services/dialog.service';
import { OAuthService } from 'angular-oauth2-oidc';
import { AuthState } from 'src/app/store/reducers';
import { Store } from '@ngrx/store';
import { logout } from 'src/app/store/app.actions';

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
    private oauthService: OAuthService,
    private authStore: Store<AuthState>
  ) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + this.oauthService.getAccessToken(),
    });
    this.httpOptions = {
      headers: this.headers,
    };
  }

  checkIfTokenExpired() {
    if (this.oauthService.getAccessTokenExpiration() - Date.now() < 2000) {
      this.authStore.dispatch(logout());
    }
    return this.oauthService.getAccessTokenExpiration() - Date.now() < 2000;
  }

  //create
  createTopic(topic: Topic): Observable<any> {
    if (this.checkIfTokenExpired()) return EMPTY;
    if (topic.topicName === null || topic.description === null)
      return throwError(() => {
        return 'Topic properites cannot be assigned to null';
      });
    return this.http
      .post(this.API_URL, topic, this.httpOptions)
      .pipe(catchError(this.error.bind(this)));
  }

  //get
  getAllTopics(page: number): Observable<Topic[]> {
    if (this.checkIfTokenExpired()) return EMPTY;
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
    if (this.checkIfTokenExpired()) return EMPTY;
    return this.http
      .delete(`${this.API_URL}/${id}`, this.httpOptions)
      .pipe(catchError(this.error.bind(this)));
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
