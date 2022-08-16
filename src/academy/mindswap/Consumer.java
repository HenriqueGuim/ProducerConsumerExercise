package academy.mindswap;

import java.util.concurrent.*;

public class Consumer implements Runnable {
    private Container<Integer> container;
    private int numToproduce;

    public Consumer(Container<Integer> queue, int i) {
        container = queue;
        numToproduce = i;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(consume());
        try {
            future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
        } catch (Exception e) {
            // handle other exceptions
        } finally {
            executor.shutdownNow();
        }
    }

    private Runnable consume() {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < numToproduce; i++) {
                    synchronized (container) {
                        System.out.println(container.poll());
                    }
                }

            }
        };
    }
}
