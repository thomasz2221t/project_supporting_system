import { HttpClient } from '@angular/common/http';
import {
  Component,
  Directive,
  ElementRef,
  OnInit,
  QueryList,
  ViewChildren,
} from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { JwksValidationHandler } from 'angular-oauth2-oidc-jwks';
import { User } from 'src/app/model/user.model';
import { authCodeFlowConfig } from '../../sso-config';
import { Store } from '@ngrx/store';
import { AuthState } from 'src/app/reducers';
import { login } from 'src/app/app.actions';
import { map, Observable } from 'rxjs';

@Directive({
  selector: '.collapse',
})
export class CollapseElement {}

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit {
  @ViewChildren(CollapseElement, { read: ElementRef })
  collpsable: QueryList<ElementRef> | null;

  isLoggedIn$: Observable<boolean>;
  isLoggedOut$: Observable<boolean>;
  hasUserRole$: Observable<boolean>;
  username$: Observable<string>;

  constructor(
    private router: Router,
    private oauthService: OAuthService,
    private store: Store<AuthState>
  ) {
    this.collpsable = null;
    this.router.events.subscribe((event) => {
      if (!(event instanceof NavigationStart)) return;
      this.collpsable?.forEach((e) => {
        e.nativeElement.classList.remove('show');
      });
    });
  }

  isActive(base: string): boolean {
    return this.router.url.includes(`/${base}`);
  }

  ngOnInit(): void {
    this.isLoggedIn$ = this.store.pipe(map((state) => !!state['auth'].user));
    this.isLoggedOut$ = this.store.pipe(map((state) => !state['auth'].user));
    this.hasUserRole$ = this.store.pipe(
      map(
        (state) =>
          !!state['auth'].user && state['auth'].user.roles.includes('user')
      )
    );
    this.username$ = this.store.pipe(
      map((state) => {
        if (!!state['auth'].user) return state['auth'].user.username;
      })
    );
    this.configureSingleSignIn();
  }

  configureSingleSignIn() {
    this.oauthService.configure(authCodeFlowConfig);
    this.oauthService.tokenValidationHandler = new JwksValidationHandler();
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then((e) => {
      if (this.token) this.getUserInfo();
    });
  }

  login() {
    this.oauthService.initCodeFlow();
    if (this.token) {
      this.getUserInfo();
    }
  }

  getUserInfo() {
    console.log(this.token);
    let user = new User();
    user.id = this.token.sub;
    user.username = this.token.preferred_username
      ? this.token.preferred_username
      : '';
    user.roles = this.token.realm_access.roles
      ? this.token.realm_access.roles
      : [];
    this.store.dispatch(login({ user }));
  }

  logout() {
    this.oauthService.logOut(true);
  }

  get token() {
    let claims: any = this.oauthService.getIdentityClaims();
    return claims ? claims : null;
  }

  ngAfterViewInit() {}
}
