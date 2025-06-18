package saga.common;

public interface MessageHandler<T extends Message> {
    void handle(T message);
}
