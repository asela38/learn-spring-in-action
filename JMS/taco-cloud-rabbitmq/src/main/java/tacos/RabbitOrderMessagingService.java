package tacos;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.model.TacoOrder;

@Service
public class RabbitOrderMessagingService {
    private final RabbitTemplate rabbit;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }


    public void sendOrder(TacoOrder tacoOrder) {
        rabbit.send("tacocloud.order", rabbit.getMessageConverter().toMessage(tacoOrder, new MessageProperties()));

    }

}
