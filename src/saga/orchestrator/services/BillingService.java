package saga.orchestrator.services;

import saga.common.BillOrderCommand;
import saga.common.BillingCompleted;
import saga.common.MessageBus;

public class BillingService {
    public BillingService(MessageBus bus) {
        bus.subscribe(BillOrderCommand.class, cmd -> {
            System.out.println("BillingService: Billing order " + cmd.orderId());
            // Simulate processing
            bus.publish(new BillingCompleted(cmd.orderId()));
        });
    }
}