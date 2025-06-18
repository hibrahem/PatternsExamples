package saga.orchestrator.services;

import saga.common.MessageBus;
import saga.common.ShipOrderCommand;
import saga.common.ShippingCompleted;

public class ShippingService {
    public ShippingService(MessageBus bus) {
        bus.subscribe(ShipOrderCommand.class, cmd -> {
            System.out.println("Orchestrator.services.ShippingService: Shipping order " + cmd.orderId());
            // Simulate processing
            bus.publish(new ShippingCompleted(cmd.orderId()));
        });
    }
}
