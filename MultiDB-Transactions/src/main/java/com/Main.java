package com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ApplicationRunner applicationContext(PersonDao dao, Service service) {
        return context -> {


            log.info("From first : {}", service.addPerson(PersonDao.Source.ONE, "Subject 001"));
            log.info("From two : {}", service.addPerson(PersonDao.Source.TWO, "Subject 002"));

            try {
                service.add10PeopleForAll(1001, 2002);
            } catch (Throwable throwable) {
                log.error("Error Occurred : {}", throwable.getMessage());
            }

            dao.findAll(PersonDao.Source.ONE).forEach( record -> log.info("From first : {}", record));

            dao.findAll(PersonDao.Source.TWO).forEach( record -> log.info("From two : {}", record));


        };
    }
}
