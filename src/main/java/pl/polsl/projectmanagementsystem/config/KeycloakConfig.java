package pl.polsl.projectmanagementsystem.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

public class KeycloakConfig {

    static Keycloak keycloak = null;

    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            return KeycloakBuilder.builder()
                    .grantType(CLIENT_CREDENTIALS)
                    .serverUrl("http://localhost:28080/auth")
                    .realm("management")
                    .clientId("admin-role")
                    .clientSecret("5367T0EL98wrUDroyCk79NRGnkQVNKrn")
                    .build();
        }
        return keycloak;
    }
}