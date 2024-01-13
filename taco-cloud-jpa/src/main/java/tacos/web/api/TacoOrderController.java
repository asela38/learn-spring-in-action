package tacos.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.data.jdbc.OrderDataRepository;
import tacos.data.jdbc.TacoRepository;
import tacos.model.Taco;
import tacos.model.TacoOrder;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RestController
@RequestMapping(path = "/api/tacosOrder",
produces = "application/json")
@CrossOrigin(origins = "https://tacocloud:8080")
public class TacoOrderController {

    private final OrderDataRepository orderDataRepository;

    public TacoOrderController(OrderDataRepository orderDataRepository) {
        this.orderDataRepository = orderDataRepository;
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder patch) {
        TacoOrder tacoOrder = orderDataRepository.findById(orderId).get();
        patchIfNotNull(tacoOrder::setDeliveryName, tacoOrder::getDeliveryName);
        patchIfNotNull(tacoOrder::setDeliveryStreet, tacoOrder::getDeliveryStreet);
        patchIfNotNull(tacoOrder::setDeliveryCity, tacoOrder::getDeliveryCity);
        patchIfNotNull(tacoOrder::setDeliveryState, tacoOrder::getDeliveryState);
        patchIfNotNull(tacoOrder::setDeliveryZip, tacoOrder::getDeliveryZip);
        patchIfNotNull(tacoOrder::setCcNumber, tacoOrder::getCcNumber);
        patchIfNotNull(tacoOrder::setCcExpiration, tacoOrder::getCcExpiration);
        patchIfNotNull(tacoOrder::setCcCVV, tacoOrder::getCcCVV);
        return orderDataRepository.save(tacoOrder);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
          orderDataRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }


    private void patchIfNotNull(Consumer<String> consumer, Supplier<String> supplier) {
        Optional.ofNullable(supplier.get()).ifPresent(consumer::accept);
    }
}
