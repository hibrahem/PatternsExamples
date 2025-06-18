package circuitbreaker;

public class Delay {
    static void simulateDelayOf(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}