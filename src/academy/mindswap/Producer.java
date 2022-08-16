package academy.mindswap;

import java.util.concurrent.*;

public class Producer implements Runnable {

    private Container<Integer> container;
    private int numToProduce;
    public Producer(Container<Integer> queue, int i) {
        container = queue;
        numToProduce = i;

    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(produce());
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


    private Runnable produce(){
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < numToProduce; i++) {

                    synchronized (container){
                        container.offer(i+1);
                    }
                }
                System.out.println("This cooker has ended is day.");
            }
        };
    }
}
