package saga.choreograpphy;

import saga.choreograpphy.services.BillingService;
import saga.choreograpphy.services.OrderService;
import saga.choreograpphy.services.ShippingService;
import saga.common.MessageBus;

public class Main {
    public static void main(String[] args) {
        MessageBus bus = new MessageBus();
        new BillingService(bus);
        new ShippingService(bus);
        new OrderService(bus).placeOrder("ORDER-321");
    }
}
