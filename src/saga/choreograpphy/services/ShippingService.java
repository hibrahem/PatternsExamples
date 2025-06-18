package saga.choreograpphy.services;

import saga.common.BillingCompleted;
import saga.common.MessageBus;
import saga.common.ShippingCompleted;

public class ShippingService {
    public ShippingService(MessageBus eventBus) {
        eventBus.subscribe(BillingCompleted.class, event -> {
            System.out.println("ShippingService: Shipping order " + event.orderId());
            eventBus.publish(new ShippingCompleted(event.orderId()));
        });
    }
}
