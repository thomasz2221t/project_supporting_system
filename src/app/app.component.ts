import { Component } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { login, logout } from './store/app.actions';
import { AuthState } from './store/reducers';
import { TopicsState } from './topics/store/reducers';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from './sso-config';
import { JwksValidationHandler } from 'angular-oauth2-oidc-jwks';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'project-supporting-system-front-end';

  constructor(
    private authStore: Store<AuthState>,
    private router: Router,
    private oauthService: OAuthService
  ) {}

  ngOnInit() {
    this.oauthService.configure(authCodeFlowConfig);
    this.oauthService.tokenValidationHandler = new JwksValidationHandler();
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then();

    const userProfile = localStorage.getItem('user');
    if (userProfile) {
      this.authStore.dispatch(login({ user: JSON.parse(userProfile) }));
    }
    this.router.events.subscribe((e) => {
      if (this.oauthService.tokenEndpoint == null) return;
      if (e instanceof NavigationStart) {
        if (!!this.oauthService.getAccessToken()) {
          if (
            this.oauthService.getAccessTokenExpiration() - Date.now() <
            2000
          ) {
            this.authStore.dispatch(logout());
          } else if (
            this.oauthService.getAccessTokenExpiration() - Date.now() <
            150000
          ) {
            this.oauthService
              .refreshToken()
              .then()
              .catch(() => this.authStore.dispatch(logout()));
          }
        }
      }
    });
  }
}
