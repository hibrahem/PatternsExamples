package saga.choreograpphy.services;

import saga.common.BillingCompleted;
import saga.common.MessageBus;
import saga.common.OrderPlaced;

public class BillingService {
    public BillingService(MessageBus eventBus) {
        eventBus.subscribe(OrderPlaced.class, event -> {
            System.out.println("BillingService: Billing order " + event.orderId());
            eventBus.publish(new BillingCompleted(event.orderId()));
        });
    }
}