package com.asela;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class MainFileWriterJava {

    public static void main(String[] args) {
        SpringApplication.run(MainFileWriterJava.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(FileWriterGateway fileWriterGateway) {
        return args -> {
            fileWriterGateway.writeToFile("test.txt", "Hello! " + LocalDateTime.now());
            log.info("Running...");
        };
    }
}
