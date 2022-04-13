import { AuthConfig } from 'angular-oauth2-oidc';
import { environment } from '../environments/environment';

export const authCodeFlowConfig: AuthConfig = {
  issuer: environment.keycloakConfig.issuer,

  redirectUri: environment.keycloakConfig.redirectUri,

  clientId: environment.keycloakConfig.clientId,

  responseType: 'code',

  revocationEndpoint: `${environment.keycloakConfig.issuer}/protocol/openid-connect/revoke`,

  scope: environment.keycloakConfig.scope,

  logoutUrl: `${environment.keycloakConfig.issuer}/protocol/openid-connect/logout`,

  requireHttps: false,

  showDebugInformation: true,
};
