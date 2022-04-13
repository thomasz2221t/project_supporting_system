import { Injectable } from '@angular/core';
import { Actions, ofType, createEffect } from '@ngrx/effects';
import { tap } from 'rxjs';
import { AuthActions } from './action-types';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable()
export class AppEffects {
  login$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(AuthActions.login),
        tap((action) => {
          localStorage.setItem('user', JSON.stringify(action.user));
        })
      ),
    { dispatch: false }
  );

  logout$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(AuthActions.logout),
        tap(() => {
          localStorage.removeItem('user');
          this.oauthService.revokeTokenAndLogout();
          this.oauthService.logOut();
        })
      ),
    { dispatch: false }
  );

  constructor(private actions$: Actions, private oauthService: OAuthService) {}
}
