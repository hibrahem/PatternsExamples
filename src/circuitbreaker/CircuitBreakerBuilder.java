package circuitbreaker;

import java.time.Duration;

public class CircuitBreakerBuilder {
    static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig initializeCircuitBreaker() {
        var circuitBreakerConfig = io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                // The failure rate threshold (in percentage) that will trigger the circuit to open
                // When the failure rate is >= 30%, the circuit will transition from CLOSED to OPEN
                .failureRateThreshold(20)

                // How long the circuit should stay OPEN before transitioning to HALF-OPEN
                // During this time, all calls will be rejected immediately without attempting to call the service
                .waitDurationInOpenState(Duration.ofSeconds(2))

                // Number of calls allowed in HALF-OPEN state before the circuit decides whether to
                // go back to CLOSED (if calls succeed) or back to OPEN (if calls fail)
                // We allow 2 calls to test if the service has recovered
                .permittedNumberOfCallsInHalfOpenState(2)

                // The size of the sliding window that stores the results of the most recent calls
                // This window is used to calculate the failure rate
                // A smaller window (5) means the circuit will react more quickly to failures
                // In production, this might be larger (e.g., 100) for more stability
                .slidingWindowSize(5)

                // Minimum number of calls that must be made before the circuit can open
                // This prevents the circuit from opening too early when there haven't been enough calls
                // to make a meaningful decision about the service's health
                // We use 3 calls for demonstration, but in production, this might be higher
                .minimumNumberOfCalls(3)
                .build();
        return circuitBreakerConfig;
    }
}