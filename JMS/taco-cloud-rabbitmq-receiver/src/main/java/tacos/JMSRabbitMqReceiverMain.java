package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Slf4j
@ComponentScan
public class JMSRabbitMqReceiverMain {
    public static void main(String[] args) {
        SpringApplication.run(JMSRabbitMqReceiverMain.class, args);
    }

    @Bean
    @Profile("!listener")
    public CommandLineRunner runner_rabbit(JMSOrderMessageReceiver receiver) {
        return  args -> {
            log.info("Pull one : {}", receiver.receiveOrder() );
        };
    }
}
