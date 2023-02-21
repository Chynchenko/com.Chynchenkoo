package controller;
import model.Order;
import service.OrderService;
import java.util.Optional;

public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    public String create() {
        final Order order = orderService.create();
        return order.getOrderId();
    }


    public Optional<Order> get(final String id) {
        return orderService.get(id);
    }


    public void delete(final String id) {
        orderService.delete(id);
    }
}