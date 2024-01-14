package com;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

public class RestIngredientService {

    private RestTemplate restTemplate;

    public RestIngredientService(String accessToken) {
        this.restTemplate = new RestTemplate();
        if(accessToken != null) {
            this.restTemplate.getInterceptors()
                    .add(getBearerTokenInterceptor(accessToken));
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                httpRequest.getHeaders().add("Authorization", "Bearer " + accessToken);
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }
        };
    }

    public Iterable<Ingredient> findAll() {
        return Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/ingredients", Ingredient[].class));
    }


    public Ingredient addIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);
    }
}
