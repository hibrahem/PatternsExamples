package circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import java.util.function.Supplier;

/**
 * This example demonstrates the Circuit Breaker pattern using Resilience4j.
 * The pattern prevents a system from making calls to a failing service,
 * allowing it to recover and preventing cascading failures.
 */
public class CircuitBreakerExample {

    public static void main(String[] args) {
        // Create a payment service instance
        PaymentService paymentService = new PaymentService();

        // Configure the circuit breaker with settings that make it easier to demonstrate the pattern
        var circuitBreakerConfig = CircuitBreakerBuilder.initializeCircuitBreaker();

        // Create a circuit breaker registry
        var registry = CircuitBreakerRegistry.of(circuitBreakerConfig);

        // Create a circuit breaker instance
        var circuitBreaker = registry.circuitBreaker("paymentService");

        // Add event listeners to monitor circuit breaker state changes
        MonitorCircuitBreaker.monitor(circuitBreaker);

        // Wrap the payment service with circuit breaker
        var decoratedPaymentService = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> paymentService.processPayment("123"));

        System.out.println("Starting Circuit Breaker Example");
        System.out.println("----------------------------------------");
        System.out.println("Phase 1: Service is up - All calls should succeed");
        System.out.println("----------------------------------------");

        // Phase 1: Service is up
        paymentService.setServiceDown(false);
        for (int i = 0; i < 3; i++) {
            callPaymentService(i, decoratedPaymentService);
            printCircuitBreakerMetrics(circuitBreaker);
        }

        System.out.println("\n----------------------------------------");
        System.out.println("Phase 2: Service is down - Circuit should open");
        System.out.println("----------------------------------------");

        // Phase 2: Service is down
        paymentService.setServiceDown(true);
        for (int i = 0; i < 5; i++) {
            callPaymentService(i, decoratedPaymentService);
            printCircuitBreakerMetrics(circuitBreaker);
            Delay.simulateDelayOf(500);
        }

        System.out.println("\n----------------------------------------");
        System.out.println("Phase 3: Service is up again - Circuit should go to half-open and then close");
        System.out.println("----------------------------------------");

        // Phase 3: Service is up again
        paymentService.setServiceDown(false);
        for (int i = 0; i < 3; i++) {
            callPaymentService(i, decoratedPaymentService);
            printCircuitBreakerMetrics(circuitBreaker);
            Delay.simulateDelayOf(500);
        }
    }

    private static void callPaymentService(int i, Supplier<String> decoratedPaymentService) {
        try {
            System.out.println("\nAttempting payment " + (i + 1));
            String result = decoratedPaymentService.get();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printCircuitBreakerMetrics(CircuitBreaker circuitBreaker) {
        System.out.println("Circuit Breaker State: " + circuitBreaker.getState());
        System.out.println("Failure Rate: " + String.format("%.1f", circuitBreaker.getMetrics().getFailureRate()) + "%");
        System.out.println("Successful Calls: " + circuitBreaker.getMetrics().getNumberOfSuccessfulCalls());
        System.out.println("Failed Calls: " + circuitBreaker.getMetrics().getNumberOfFailedCalls());
    }

}

