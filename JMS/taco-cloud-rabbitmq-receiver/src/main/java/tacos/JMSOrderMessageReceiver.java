package tacos;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tacos.model.TacoOrder;

import java.util.Optional;

@Component
@Profile("!listener")
public class JMSOrderMessageReceiver {

    private RabbitTemplate rabbitTemplate;
    private MessageConverter converter;

    @Autowired
    public JMSOrderMessageReceiver(RabbitTemplate jmsTemplate) {
        this.rabbitTemplate = jmsTemplate;
        this.converter = rabbitTemplate.getMessageConverter();
    }

    public TacoOrder receiveOrder()  {
        return Optional.ofNullable(rabbitTemplate.receive("kitchens.central")).map(converter::fromMessage)
                .filter(TacoOrder.class::isInstance)
                .map(TacoOrder.class::cast)
                .orElse(null);
    }
}
