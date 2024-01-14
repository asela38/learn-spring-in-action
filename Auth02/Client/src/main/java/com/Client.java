package com;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.metamodel.internal.JpaStaticMetaModelPopulationSetting;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
@Slf4j
public class Client {
    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Bean
  //  @RequestScope
    public RestIngredientService ingredientService(OAuth2AuthorizedClientService clientService) {
        SecurityContextHolder.createEmptyContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        if(authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            if("tacoadmin-client".equals(clientRegistrationId)) {
                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oAuth2AuthenticationToken.getName());
                accessToken = client.getAccessToken().getTokenValue();
            }
        }
        return new RestIngredientService(accessToken);
    }

    @Bean
    public ApplicationRunner applicationRunner(RestIngredientService ingredientService) {
        return args -> {
            ingredientService.addIngredient(Ingredient.builder().id("TEST").name("TEST").type(Ingredient.Type.WRAP).build());

            ingredientService.findAll().forEach(ingredient -> log.info(" {} ", ingredient));
        };
    }
}
