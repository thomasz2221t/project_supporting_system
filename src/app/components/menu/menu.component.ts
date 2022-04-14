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
import { select, Store } from '@ngrx/store';
import { AuthState } from 'src/app/store/reducers';
import { login, logout } from 'src/app/store/app.actions';
import { Observable } from 'rxjs';
import {
  isLoggedIn,
  isLoggedOut,
  getUsername,
  hasUserRole,
} from 'src/app/store/app.selectors';

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
  collapsable: QueryList<ElementRef> | null;

  isLoggedIn$: Observable<boolean>;
  isLoggedOut$: Observable<boolean>;
  hasUserRole$: Observable<boolean>;
  username$: Observable<string>;

  constructor(
    private router: Router,
    private oauthService: OAuthService,
    private store: Store<AuthState>
  ) {
    this.collapsable = null;
    this.router.events.subscribe((event) => {
      if (!(event instanceof NavigationStart)) return;
      this.collapsable?.forEach((e) => {
        e.nativeElement.classList.remove('show');
      });
    });
  }

  isActive(base: string): boolean {
    return this.router.url.includes(`/${base}`);
  }

  ngOnInit(): void {
    this.isLoggedIn$ = this.store.pipe(select(isLoggedIn));
    this.isLoggedOut$ = this.store.pipe(select(isLoggedOut));
    this.hasUserRole$ = this.store.pipe(select(hasUserRole));
    this.username$ = this.store.pipe(select(getUsername));
  }


  login() {
    this.oauthService.initCodeFlow();
    if (this.token) {
      this.getUserInfo();
    }
  }

  getUserInfo() {
    let user: User = {
      id: this.token.sub,
      username: this.token.preferred_username
        ? this.token.preferred_username
        : '',
      roles: this.token.realm_access.roles ? this.token.realm_access.roles : [],
    };

    this.store.dispatch(login({ user: user }));
  }

  logout() {
    this.store.dispatch(logout());
  }

  get token() {
    let claims: any = this.oauthService.getIdentityClaims();
    return claims ? claims : null;
  }

  ngAfterViewInit() {}
}
