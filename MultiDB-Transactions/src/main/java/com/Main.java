package com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ApplicationRunner applicationContext(PersonDao dao) {
        return context -> {
            log.info("From first : {}", dao.find(PersonDao.Source.ONE, "Subject 1"));
            log.info("From two : {}", dao.find(PersonDao.Source.TWO, "Subject 1"));
        };
    }
}
