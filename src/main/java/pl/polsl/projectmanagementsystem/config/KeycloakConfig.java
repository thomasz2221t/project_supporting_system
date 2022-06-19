package pl.polsl.projectmanagementsystem.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
@Configuration
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
                    .clientSecret("QFZj3qkxD2aYQ0v8S3enL2jVY6GGqXvA")
                    .build();
        }
        return keycloak;
    }
}