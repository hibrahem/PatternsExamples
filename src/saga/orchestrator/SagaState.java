package saga.orchestrator;

public class SagaState {
    public final String orderId;
    public SagaStep currentStep;

    public SagaState(String orderId) {
        this.orderId = orderId;
        this.currentStep = SagaStep.ORDER_PLACED;
    }
}
