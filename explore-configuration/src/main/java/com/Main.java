package com;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationProperties("com.main")
@Data
@Slf4j
public class Main {

    private  String message;
    private  String version;

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
          log.info("Message {}", message);
          log.info("version {}", version);
        };
    }
}
