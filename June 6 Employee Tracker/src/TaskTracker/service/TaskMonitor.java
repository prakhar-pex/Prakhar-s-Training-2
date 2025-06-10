package TaskTracker.service;

import TaskTracker.model.Task;

import java.util.List;

public class TaskMonitor extends Thread {
    private final TaskManager taskManager;
    private volatile boolean running = true;
    public TaskMonitor(TaskManager taskManager) {
        this.taskManager = taskManager;
        setDaemon(true); // This will allow the program to exit even if this thread is running
    }
    public void stopMonitoring() {
        running = false;
        interrupt();
    }
    @Override
    public void run() {
        while (running) {
            try {
                List<Task> overdueTasks = taskManager.getOverdueTasks();
                if (!overdueTasks.isEmpty()) {
                    System.out.println("\n=== Overdue Tasks ===");
                    overdueTasks.forEach(System.out::println);
                    System.out.println("====================\n");
                }
                Thread.sleep(60000); // 1 minute
            } catch (InterruptedException e) {
                if (!running) {
                    break;
                }
            }
        }
    }
}
