package saga.orchestrator;

import saga.common.MessageBus;
import saga.common.OrderPlaced;
import saga.orchestrator.services.BillingService;
import saga.orchestrator.services.ShippingService;

public class Main {
    public static void main(String[] args) {
        MessageBus bus = new MessageBus();
        SagaStore sagaStore = new SagaStore();

        new OrderSaga(bus, sagaStore);
        new BillingService(bus);
        new ShippingService(bus);

        System.out.println("Starting new order flow...");
        bus.publish(new OrderPlaced("ORDER-789"));
    }
}

