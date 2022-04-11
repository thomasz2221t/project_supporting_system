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
import { authCodeFlowConfig } from '../../sso-config';

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

  user:
    | {
        username?: string;
        roles?: string[];
      }
    | undefined;

  constructor(private router: Router, private oauthService: OAuthService) {
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
    this.configureSingleSignIn();
    if (this.token) {
      this.getUserInfo();
    }
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
    if (this.user === undefined) this.user = {};
    this.user.username = this.token.preferred_username
      ? this.token.preferred_username
      : '';
    this.user.roles = this.token.realm_access.roles;
  }

  logout() {
    this.oauthService.logOut();
    // this.oauthService.token;
  }

  hasUserRole() {
    return this.user?.roles?.includes('user');
  }

  get token() {
    let claims: any = this.oauthService.getIdentityClaims();
    return claims ? claims : null;
  }

  ngAfterViewInit() {}
}
