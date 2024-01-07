package tacos;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tacos.data.jdbc.IngredientDataRepository;

@SpringBootApplication
@ComponentScan
public class TacoCloudApplicationCassandra implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplicationCassandra.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}

	@Bean
	public ApplicationRunner dataLoad(IngredientDataRepository repository) {
		return args -> {
			IngredientUtils.getIngredients().forEach(repository::save);
		};
	}
}
