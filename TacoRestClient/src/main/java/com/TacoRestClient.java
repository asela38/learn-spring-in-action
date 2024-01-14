package com;

import com.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class TacoRestClient {

    @Autowired
    public RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TacoRestClient.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ApplicationRunner runner() {
        return  args -> {
          log.info("ingredient {}",  getIngredientById("COTO"));
          log.info("ingredient {}",  getIngredientByIdThroughEntity("COTO"));
          updateIngredient(Ingredient.builder().id("NOTO").name("CORN Tortilla").build());
          log.info("ingredient {}",  getIngredientById("NOTO"));

            deleteIngredient(Ingredient.builder().id("NOTO").name("CORN Tortilla").build());
            try {
                log.info("ingredient {}", getIngredientById("NOTO"));
            } catch (HttpClientErrorException exception) {
                log.error("Error", exception);
            }

        };
    }


    public Ingredient getIngredientById(String ingredientId) {
        return restTemplate.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, ingredientId);
    }

    public ResponseEntity<Ingredient> getIngredientByIdThroughEntity(String ingredientId) {
        return restTemplate.getForEntity("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, ingredientId);
    }

    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/data-api/ingredients/{id}", ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/data-api/ingredients/{id}", ingredient.getId());
    }
}
