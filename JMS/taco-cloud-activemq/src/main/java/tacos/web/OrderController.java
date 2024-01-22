package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.JmsOrderMessagingService;
import tacos.data.jdbc.OrderDataRepository;
import tacos.model.TacoOrder;
import tacos.model.User;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderDataRepository orderRepository;
    private final JmsOrderMessagingService jmsOrderMessagingService;

    @Autowired
    public OrderController(OrderDataRepository orderRepository, JmsOrderMessagingService jmsOrderMessagingService) {
        this.orderRepository = orderRepository;
        this.jmsOrderMessagingService = jmsOrderMessagingService;
    }

    @GetMapping("/current")
    public String orderFrom() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user
    ) {
        if(errors.hasErrors()) {
            return "orderForm";
        }
        log.info("order submitted: {}", order);
      //  order.setUser(user);
        orderRepository.save(order);
        jmsOrderMessagingService.sendOrder(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
