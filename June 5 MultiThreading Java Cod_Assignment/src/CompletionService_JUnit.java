import java.util.concurrent.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionService_JUnit {

    //WORKING:
    //    You submit tasks using submit(...).
    //    As tasks complete, their results are added to an internal queue.
    //    You retrieve results using take() or poll() — and they are returned in the order
    //    of completion, not in the order you submitted them.
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Wrap the ExecutorService with a CompletionService.
        // Internally, it uses a blocking queue to store completed task results.
        java.util.concurrent.CompletionService<String> service = new ExecutorCompletionService<>(executor);

        //submit tasks, task1.
        service.submit(() -> {
            Thread.sleep(10000); // 10 sec
            return "Task 1 (10 sec)";
        });

        service.submit(() -> {
            Thread.sleep(1000); // 1 sec
            return "Task 2 (1 sec)";
        });

        service.submit(() -> {
            Thread.sleep(1000); // 1 sec
            return "Task 3 (1 sec)";
        });

        // Take results as they finish — doesn't wait for all, just picks completed ones
        for (int i = 0; i < 3; i++) {


            //You submit a Callable. The executor runs it in a background thread.
            //take() blocks (waits) until a task completes and puts its result into the queue.
            //Once a result is ready, take() unblocks and gives you that result.
            Future<String> result = service.take(); // waits only for finished ones
            System.out.println("Completed: " + result.get());
        }

        executor.shutdown();
    }
}
