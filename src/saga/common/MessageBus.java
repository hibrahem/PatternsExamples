package saga.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageBus {
    private final Map<Class<?>, List<MessageHandler<?>>> handlers = new HashMap<>();

    public <T extends Message> void subscribe(Class<T> type, MessageHandler<T> handler) {
        handlers.computeIfAbsent(type, k -> new ArrayList<>()).add(handler);
    }

    public void publish(Message message) {
        List<MessageHandler<?>> subs = handlers.get(message.getClass());
        if (subs != null) {
            for (MessageHandler<?> h : subs) {
                @SuppressWarnings("unchecked")
                MessageHandler<Message> handler = (MessageHandler<Message>) h;
                handler.handle(message);
            }
        }
    }
}