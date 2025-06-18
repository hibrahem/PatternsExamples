package saga.orchestrator;

import saga.common.*;

public class OrderSaga {
    private final MessageBus bus;
    private final SagaStore sagaStore;

    public OrderSaga(MessageBus bus, SagaStore sagaStore) {
        this.bus = bus;
        this.sagaStore = sagaStore;

        bus.subscribe(OrderPlaced.class, this::onOrderPlaced);
        bus.subscribe(BillingCompleted.class, this::onBillingCompleted);
        bus.subscribe(ShippingCompleted.class, this::onShippingCompleted);
    }

    private void onOrderPlaced(OrderPlaced event) {
        SagaState state = sagaStore.getOrCreate(event.orderId());
        if (state.currentStep == SagaStep.ORDER_PLACED) {
            System.out.println("Saga: Order placed, sending BillOrderCommand");
            state.currentStep = SagaStep.BILLING_COMPLETED;
            sagaStore.update(state);
            bus.publish(new BillOrderCommand(event.orderId()));
        }
    }

    private void onBillingCompleted(BillingCompleted event) {
        SagaState state = sagaStore.getOrCreate(event.orderId());
        if (state.currentStep == SagaStep.BILLING_COMPLETED) {
            System.out.println("Saga: Billing completed, sending ShipOrderCommand");
            bus.publish(new ShipOrderCommand(event.orderId()));
            state.currentStep = SagaStep.SHIPPING_COMPLETED;
            sagaStore.update(state);
        }
    }

    private void onShippingCompleted(ShippingCompleted event) {
        SagaState state = sagaStore.getOrCreate(event.orderId());
        if (state.currentStep == SagaStep.SHIPPING_COMPLETED) {
            System.out.println("Saga: Shipping completed. Saga done for " + event.orderId());
            // Mark as fully completed, optionally remove or archive
            sagaStore.update(state); // last update
        }
    }
}

