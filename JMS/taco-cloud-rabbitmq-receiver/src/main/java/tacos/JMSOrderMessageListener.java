package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tacos.model.TacoOrder;

@Component
@Profile("listener")
@Slf4j
public class JMSOrderMessageListener {

    @RabbitListener(queues = "kitchens.central")
    public void receiveOrder(TacoOrder tacoOrder) {
        log.info("{}", tacoOrder);
    }
}
