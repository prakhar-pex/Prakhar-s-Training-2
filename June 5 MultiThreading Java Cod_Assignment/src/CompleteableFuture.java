//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//public class CompleteableFuture {
//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//
//// supplyAsync -> Runs a task asynchronously (in a background thread).
////It Immediately returns a CompletableFuture object that will eventually hold the result.
////i.e. it returns a CompletableFuture<String> that will eventually contain "Task A completed (10s)"
//        CompletableFuture<String> taskA = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "Task A completed (10s)";
//        });
//
//        // Task B: 1 sec
//        CompletableFuture<String> taskB = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "Task B completed (1s)";
//        });
//
//        // Task C: 1 sec
//        CompletableFuture<String> taskC = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "Task C completed (1s)";
//        });
//
//// thenAccept(...) is a callback. -> Runs a callback after the future is complete.
//// Future is kind of a common place where the results are stored, and we can acccess them,
//// if the result was completed and stored in future.
//// It allows you to use the result without waiting for others.
//// It executes as soon as that specific task is done, with the result passed to result -> ....
//// So, results are printed immediately when each task completes.
//// => This is non-blocking — the main thread doesn’t wait for all; it listens for results and prints them as they arrive
//        taskA.thenAccept(result -> System.out.println("Received: " + result));
//        taskB.thenAccept(result -> System.out.println("Received: " + result));
//        taskC.thenAccept(result -> System.out.println("Received: " + result));
//
//
//        // Prevent main from exiting early (wait for all tasks)
//        //allOf: Waits for all specified futures to finish (using join).
//        CompletableFuture.allOf(taskA, taskB, taskC).join();
//    }
//}
//
//
////Extra Pointers:
////WHY NEED supplyAsync:
//
////It allows the Code inside supplyAsync to run on a different thread and ,
////the Main thread moves on
////Result is delivered when it’s ready, using callbacks like thenAccept(...)

//

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompleteableFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // supplyAsync -> Runs a task asynchronously (in a background thread).
        //It Immediately returns a CompletableFuture object that will eventually hold the result.
        //i.e. it returns a CompletableFuture<String> that will eventually contain "Task A completed (10s)"
        CompletableFuture<String> taskA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task A completed (10s)";
        }, executor);

        // Task B: 1 sec
        CompletableFuture<String> taskB = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task B completed (1s)";
        }, executor);

        // Task C: 1 sec
        CompletableFuture<String> taskC = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task C completed (1s)";
        }, executor);

        // thenAccept(...) is a callback. -> Runs a callback after the future is complete.
        // Future is kind of a common place where the results are stored, and we can acccess them,
        // if the result was completed and stored in future.
        // It allows you to use the result without waiting for others.
        // It executes as soon as that specific task is done, with the result passed to result -> ....
        // So, results are printed immediately when each task completes.
        // => This is non-blocking — the main thread doesn’t wait for all; it listens for results and prints them as they arrive
        taskA.thenAccept(result -> System.out.println("Received: " + result));
        taskB.thenAccept(result -> System.out.println("Received: " + result));
        taskC.thenAccept(result -> System.out.println("Received: " + result));

        // Prevent main from exiting early (wait for all tasks)
        //allOf: Waits for all specified futures to finish (using join).
        CompletableFuture.allOf(taskA, taskB, taskC).join();

        executor.shutdown(); // Always shut down the executor after use
    }
}
