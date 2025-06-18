package saga.common;

// Events and Commands
public record OrderPlaced(String orderId) implements Message {
}
