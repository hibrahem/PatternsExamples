package circuitbreaker;

import java.util.Random;

// Simulated payment service that might fail
class PaymentService {
    private final Random random = new Random();
    private boolean isServiceDown = false;

    public String processPayment(String paymentId) {
        // Simulate a service that fails 70% of the time when it's down
        if (isServiceDown && random.nextDouble() < 0.7) {
            throw new RuntimeException("Payment service is temporarily unavailable");
        }

        Delay.simulateDelayOf(500);

        return "Payment " + paymentId + " processed successfully";
    }

    public void setServiceDown(boolean down) {
        this.isServiceDown = down;
    }
}
