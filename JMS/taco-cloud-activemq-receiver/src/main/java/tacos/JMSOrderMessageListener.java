package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.model.TacoOrder;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@Profile("listener")
@Slf4j
public class JMSOrderMessageListener {

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(TacoOrder tacoOrder) {
        log.info("{}", tacoOrder);
    }
}
