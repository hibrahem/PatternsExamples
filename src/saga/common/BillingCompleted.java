package saga.common;

public record BillingCompleted(String orderId) implements Message {
}
