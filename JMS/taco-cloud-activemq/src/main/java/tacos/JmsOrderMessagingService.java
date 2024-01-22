package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.model.TacoOrder;

import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrder(TacoOrder order) {
        jmsTemplate.convertAndSend("tacocloud.order.queue", order, this::getMessagePostProcessor);
    }

    private Message getMessagePostProcessor(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }

}
