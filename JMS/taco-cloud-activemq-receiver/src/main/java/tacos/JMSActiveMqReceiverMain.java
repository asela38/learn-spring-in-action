package tacos;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import tacos.model.TacoOrder;

import java.util.Map;

@SpringBootApplication
@Slf4j
@ComponentScan
public class JMSActiveMqReceiverMain {
    public static void main(String[] args) {
        SpringApplication.run(JMSActiveMqReceiverMain.class, args);
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter(){
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");
        messageConverter.setTypeIdMappings(Map.of("order", TacoOrder.class));
         messageConverter.setObjectMapper(new ObjectMapper()
                 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                 .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
                 .configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false)
                 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
         );
        return messageConverter;
    }



    @Bean
    public CommandLineRunner runner(JMSOrderMessageReceiver receiver) {
        return  args -> {
            log.info("Pull one : {}", receiver.receiveOrder() );
        };
    }
}
