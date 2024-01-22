package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.model.TacoOrder;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class JMSOrderMessageReceiver {

    private JmsTemplate jmsTemplate;
    private MessageConverter converter;

    @Autowired
    public JMSOrderMessageReceiver(JmsTemplate jmsTemplate, MessageConverter converter) {
        this.jmsTemplate = jmsTemplate;
        this.converter = converter;
    }

    public TacoOrder receiveOrder() throws JMSException {
        Message message = jmsTemplate.receive("tacocloud.order.queue");
        return (TacoOrder) converter.fromMessage(message);
    }
}
