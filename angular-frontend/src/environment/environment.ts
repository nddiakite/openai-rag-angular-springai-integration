export const environment = {
  production: false,

  serverApiUrl: 'http://localhost:8082/api',
  // serverApiUrl: 'http://79.137.38.153:8082/api',

  keycloak: {
    // Url of the Identity Provider
    issuer: 'https://keycloak.ooguy.com:8443/',
    // Realm
    realm: 'timetable-oauth',
    clientId: 'angular-client',
  }
};
