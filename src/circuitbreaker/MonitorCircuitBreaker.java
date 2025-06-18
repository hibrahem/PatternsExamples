package circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

public class MonitorCircuitBreaker {
    static void monitor(CircuitBreaker circuitBreaker) {
        circuitBreaker.getEventPublisher()
                .onCallNotPermitted(event -> System.out.println("Circuit Breaker: Call not permitted - circuit is " + circuitBreaker.getState()))
                .onError(event -> System.out.println("Circuit Breaker: Error occurred - " + event.getThrowable().getMessage()))
                .onStateTransition(event -> System.out.println("Circuit Breaker: State changed from " +
                        event.getStateTransition().getFromState() + " to " +
                        event.getStateTransition().getToState()))
                .onSuccess(event -> System.out.println("Circuit Breaker: Call succeeded"))
                .onIgnoredError(event -> System.out.println("Circuit Breaker: Error ignored - " + event.getThrowable().getMessage()))
                .onReset(event -> System.out.println("Circuit Breaker: Reset to initial state"));
    }
}