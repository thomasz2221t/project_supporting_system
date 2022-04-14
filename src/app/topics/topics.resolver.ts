import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Store } from '@ngrx/store';
import { finalize, first, Observable, tap } from 'rxjs';
import { AppState } from 'src/app/store/app-store';
import { loadAllTopics } from './store/topics.actions';

@Injectable()
export class TopicsResolver implements Resolve<any> {
  loading = false;

  constructor(private store: Store<AppState>) {}
  resolve(
    router: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {
    return this.store.pipe(
      tap(() => {
        if (!this.loading) {
          this.loading = true;
          this.store.dispatch(loadAllTopics());
        }
      }),
      first(),
      finalize(() => (this.loading = false))
    );
  }
}