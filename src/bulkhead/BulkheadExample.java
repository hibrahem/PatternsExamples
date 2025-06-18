package bulkhead;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This example demonstrates the Bulkhead Pattern, which is a fault isolation pattern.
 * The pattern is named after the watertight compartments in ships that prevent the entire
 * ship from sinking if one compartment is breached.
 * <p>
 * In software, bulkheads isolate different parts of the system to prevent cascading failures.
 * Here, we create separate thread pools for catalog and order services to ensure that
 * if one service is overwhelmed, it doesn't affect the other.
 */
public class BulkheadExample {
    private static final int TOTAL_REQUESTS = 20;

    // Thread pools with descriptive names
    private static final ExecutorService catalogPool = new ThreadPoolExecutor(
            2,                          // Minimum number of threads that will be kept alive
            3,                                      // Maximum number of threads that can be created
            60, TimeUnit.SECONDS,                   // How long excess threads will wait for new tasks
            new ArrayBlockingQueue<>(5),    // Queue to hold tasks when all threads are busy
            new ThreadFactory() {                   // Custom thread factory to name threads
                private final AtomicInteger counter = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("catalog-pool-" + counter.getAndIncrement());
                    return thread;
                }
            },
            new ThreadPoolExecutor.AbortPolicy()  // Reject new tasks when queue is full
    );

    private static final ExecutorService orderPool = new ThreadPoolExecutor(
            2,
            5,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("order-pool-" + counter.getAndIncrement());
                    return thread;
                }
            },
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {

        System.out.println("Starting Bulkhead Pattern Example");
        System.out.println("Catalog Pool: " + 2 + " core threads, " +
                3 + " max threads, queue size " + 5);
        System.out.println("Order Pool: " + 2 + " core threads, " +
                5 + " max threads, queue size " + 10);
        System.out.println("----------------------------------------");

        // Submit catalog requests
        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            int id = i;
            try {
                simulateDelayOf(100);
                printPoolStats("Before Catalog Request " + id);
                catalogPool.submit(() -> {
                    String result = callCatalogService(id);
                    System.out.println("Catalog result: " + result);
                    printPoolStats("After Catalog Request " + id);
                });
            } catch (RejectedExecutionException e) {
                System.err.println("Catalog request " + id + " rejected: Pool is full");
            }
        }

        // Submit order requests
        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            int id = i;
            try {
                simulateDelayOf(100);
                printPoolStats("Before Order Request " + id);
                orderPool.submit(() -> {
                    String result = callOrderService(id);
                    System.out.println("Order result: " + result);
                    printPoolStats("After Order Request " + id);
                });
            } catch (RejectedExecutionException e) {
                System.err.println("Order request " + id + " rejected: Pool is full");
            }
        }

        shutdownPools();
    }

    private static void printPoolStats(String context) {
        if (catalogPool instanceof ThreadPoolExecutor catalogExecutor) {
            System.out.printf("%s - Catalog Pool: Active=%d, Pool=%d, Queue=%d%n",
                    context,
                    catalogExecutor.getActiveCount(),
                    catalogExecutor.getPoolSize(),
                    catalogExecutor.getQueue().size());
        }
        if (orderPool instanceof ThreadPoolExecutor orderExecutor) {
            System.out.printf("%s - Order Pool: Active=%d, Pool=%d, Queue=%d%n",
                    context,
                    orderExecutor.getActiveCount(),
                    orderExecutor.getPoolSize(),
                    orderExecutor.getQueue().size());
        }
    }

    private static void shutdownPools() {
        catalogPool.shutdown();
        orderPool.shutdown();
    }

    // Simulated service calls
    private static String callCatalogService(int id) {
        simulateDelayOf(500);
        return "Catalog#" + id;
    }

    private static String callOrderService(int id) {
        simulateDelayOf(500);
        return "Order#" + id;
    }

    private static void simulateDelayOf(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
