package saga.choreograpphy.services;

import saga.common.MessageBus;
import saga.common.OrderPlaced;
import saga.common.ShippingCompleted;

import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private final MessageBus eventBus;
    private final Map<String, String> orderStatus = new HashMap<>();

    public OrderService(MessageBus eventBus) {
        this.eventBus = eventBus;
        // React to ShippingCompleted
        eventBus.subscribe(ShippingCompleted.class, this::onShippingCompleted);
    }

    public void placeOrder(String orderId) {
        System.out.println("OrderService: Placing order " + orderId);
        orderStatus.put(orderId, "PLACED");
        eventBus.publish(new OrderPlaced(orderId));
    }

    private void onShippingCompleted(ShippingCompleted event) {
        System.out.println("OrderService: Order shipped, completing order " + event.orderId());
        orderStatus.put(event.orderId(), "COMPLETED");
    }
}