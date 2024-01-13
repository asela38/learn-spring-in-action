package tacos;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tacos.data.jdbc.IngredientDataRepository;
import tacos.data.jdbc.TacoRepository;
import tacos.model.Ingredient;
import tacos.model.Taco;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
@ComponentScan
public class TacoCloudApplicationRestJpa implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplicationRestJpa.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login");
	}

	@Bean
	public ApplicationRunner dataLoad(IngredientDataRepository repository, TacoRepository tacoRepository) {
		return args -> {
			List<Ingredient> ingredients = IngredientUtils.getIngredients();
			ingredients.forEach(repository::save);
			Map<String, Ingredient> ingredientMap = ingredients.stream().collect(Collectors.toMap(Ingredient::getId, Function.identity()));

			tacoRepository.save(Taco.builder().name("Veg-Out")
					.ingredients(Arrays.asList(ingredientMap.get("FLTO"), ingredientMap.get("COTO"),
							ingredientMap.get("TMTO"), ingredientMap.get("LETC"), ingredientMap.get("SLSA")))
					.build());

			tacoRepository.save(Taco.builder().name("Bovine Bounty")
					.ingredients(Arrays.asList(ingredientMap.get("COTO"), ingredientMap.get("GRBF"),
							ingredientMap.get("CHED"), ingredientMap.get("JACK"), ingredientMap.get("SRCR")))
					.build());

			tacoRepository.save(Taco.builder().name("Carnivore")
					.ingredients(Arrays.asList(ingredientMap.get("FLTO"), ingredientMap.get("GRBF"),
							ingredientMap.get("CARN"), ingredientMap.get("SRCR"), ingredientMap.get("SLSA"),
							ingredientMap.get("CHED")))
					.build());
		};
	}
}
