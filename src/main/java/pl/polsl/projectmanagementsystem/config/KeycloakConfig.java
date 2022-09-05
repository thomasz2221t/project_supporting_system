package pl.polsl.projectmanagementsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
@Configuration
@Slf4j
public class KeycloakConfig {

    static Keycloak keycloak = null;

    @Value("${management.secret-key}")
    private String name;

    private static String NAME_STATIC;

    @Value("${management.secret-key}")
    public void setNameStatic(String name){
        KeycloakConfig.NAME_STATIC = name;
    }

    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            log.warn("SECRETT KEY -------------------------------------------------------------- {}", NAME_STATIC);

            return KeycloakBuilder.builder()
                    .grantType(CLIENT_CREDENTIALS)
                    .serverUrl("http://localhost:28080/auth")
                    .realm("management")
                    .clientId("admin-role")
                    .clientSecret(NAME_STATIC)
                    .build();
        }
        return keycloak;
    }
}