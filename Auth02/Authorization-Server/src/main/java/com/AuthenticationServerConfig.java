package com;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.context.IContext;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class AuthenticationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http
                .csrf().disable()
                .formLogin(Customizer.withDefaults())
                .build();
    }

    /**
     * http://localhost:9000/oauth2/authorize?response_type=code&client_id=tacoadmin-client&redirect_url=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients+deleteIngredients+openid
     *
     * http://127.0.0.1:9090/login/oauth2/code/taco-admin-client?code=Z7Zozt9kNZijoBchun3JERlFr61APHjeu3_uPfSQh-8kBDPxoel9Ss4Es-HnN46gGPWWYpk0eBnL1lkxzH6oB8l1DTS5DKjq3vyx7MHIQYKAm8GQehK_MchVkxj6Y4O3
     * http://127.0.0.1:9090/login/oauth2/code/taco-admin-client?code=vxZxLk_d8-U6F8sxruEy8qaeQmbbbyErTbtuNGIJoN9HzY8-OyT2YjE80E1YXkzjmFj8FGFpqTLBU_ddXsBwR2D05mAn9XnFY7yEMwbzBt7EODQpLEzAq0Baf-zI0Ui0
     *
     * http://127.0.0.1:9090/login/oauth2/code/taco-admin-client?code=zqxJw-7mTFHPPcXMpULCZLiEGC-S0IxYN-2ughWSuYJnB9pORTwOToX9HOHb1RoJInLJAzrcqnCfH18jApA5NAvQhvxJc2dMl0IENhf6_N2FxeKsdu_QSzboeKNdmQcL
     *
     * http://127.0.0.1:9090/login/oauth2/code/taco-admin-client?code=9vfK5e92BM-eDVQKTeM0yOVPwU2SXTeUlNxdg-6PAB9p-OhBfkxKVCWX77vGqcFLmg0U7cxYFvw5AbfBqDfkksiDpqs3cfjuNwC2lT9R_eglrJroPuqFClfPh-Ac1rua
     * curl localhost:9000/oauth2/token -H "Content-type: application/x-www-form-urlencoded" -d "grant_type=authorization_code" -d "redirect_uri=$redirect" -d "code=$code" -u taco-admin-client:secret
     *
     * {
     *   "access_token": "eyJraWQiOiJmMmFjYzU4Ny1lODVkLTQwNGItYjk4ZC01ODgyZWQwMDVkZDEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0YWNvY2hlZiIsImF1ZCI6InRhY29hZG1pbi1jbGllbnQiLCJuYmYiOjE3MDUyNDA2MjIsInNjb3BlIjpbImRlbGV0ZUluZ3JlZGllbnRzIiwid3JpdGVJbmdyZWRpZW50cyJdLCJpc3MiOiJodHRwOlwvXC9hdXRoc2VydmVyOjkwMDAiLCJleHAiOjE3MDUyNDA5MjIsImlhdCI6MTcwNTI0MDYyMiwianRpIjoiNGI5NTQ0NjgtMTZhNy00YzQ3LWE0YjktOTZlZDk3NTZmYTc3In0.u7PVSLziKXl5NlXadiiQjfPewzWGwSzkDkbI9k3TcBul8gRqmilB6zvkaipbby-BU6vI7hsSx4ueKFdO0tiAeLrChh0RVq9iO6-R4cGB_4CRDE_AYjum-mRURIDIu1Flp6Cd67v88TFUIewIKng1cBRIVohUhCAKjL6qCCkp2GZiv5KMYK1JgSGZCLVbJFSvsnUUM7PJKF44t42u_gU4cO1yK5AM-UGA2F_uaJRZ2NTpYmlkln_ugabKz03fwhM5Std6s7RaxDCA9QNiZQwwn6207WFt48FAzW54U-lIEMACdsFwcz3CpSAg5d6fFgoSGTsAi7XumoOkyQwxiNGXKA",
     *   "refresh_token": "FV-nRZscUEM7Xldfye5Nyp4Uj8Bl5ypP9urmhhI-CilerfPXDptWyN6E1jIi6FLQKRrsQED8AZ0Vg7AYlRKIwySGdNlQD-U1qFqb1s7v1S8Xuyi2UVAgTVsQvAR2KxxW",
     *   "scope": "deleteIngredients writeIngredients",
     *   "token_type": "Bearer",
     *   "expires_in": 299
     * }
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient registeredClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("tacoadmin-client")
                        .clientSecret(passwordEncoder.encode("secret"))
                        .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC) // BASIC is need if curl was used
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri(
                                "http://127.0.0.1:9090/login/oauth2/code/taco-admin-client")
                        .scope("writeIngredients")
                        .scope("deleteIngredients")
                        .scope(OidcScopes.OPENID)
                        .clientSettings(
                                clientSettings -> clientSettings.requireUserConsent(true))
                        .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return new ProviderSettings().issuer("http://authserver:9000");
    }


    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {

        RSAKey rsaKey = generateRsa();
        JWKSet jWkSet = new JWKSet(rsaKey);
        return (selector, context) -> selector.select(jWkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    private RSAKey generateRsa() throws NoSuchAlgorithmException{
        KeyPair keyPair= generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
