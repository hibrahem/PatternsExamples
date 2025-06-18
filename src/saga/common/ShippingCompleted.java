package saga.common;

public record ShippingCompleted(String orderId) implements Message {
}
