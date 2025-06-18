package saga.orchestrator;

import java.util.HashMap;
import java.util.Map;

public class SagaStore {
    private final Map<String, SagaState> storage = new HashMap<>();

    public SagaState getOrCreate(String orderId) {
        return storage.computeIfAbsent(orderId, SagaState::new);
    }

    public void update(SagaState state) {
        storage.put(state.orderId, state);
        System.out.println("SagaStore: Persisted step " + state.currentStep + " for " + state.orderId);
    }

    public SagaStep getCurrentStep(String orderId) {
        SagaState state = storage.get(orderId);
        return (state != null) ? state.currentStep : null;
    }
}