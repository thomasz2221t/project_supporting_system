export const environment = {
  production: true,
  baseUrl: 'http://localhost:8080',
  keycloakConfig: {
    issuer: 'http://localhost:28080/auth/realms/management',
    redirectUri: 'http://localhost:4200/',
    clientId: 'front-end',
    scope: 'openid profile email offline_access roles',
  },
};
