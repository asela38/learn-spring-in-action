package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.model.TacoOrder;

@Service
public class JmsOrderMessagingService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrder(TacoOrder order) {
        jmsTemplate.send(session -> session.createObjectMessage(order));
    }

}
