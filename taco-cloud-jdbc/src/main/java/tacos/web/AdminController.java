package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.jdbc.OrderRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderRepository orderRepository;

    public AdminController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/deleteOrders")
    public String deleteAllOrders() {
        orderRepository.deleteAllOrders();
        return "redirect:/admin";
    }
}
