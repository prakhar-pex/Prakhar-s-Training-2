package TaskTracker.model;
import java.time.LocalDate;


public class Task implements Comparable<Task> {
    private String id;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private TaskPriority priority;
    public Task(String id, String description, TaskStatus status, LocalDate dueDate, TaskPriority priority) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.priority = priority;
    }
    // Getters
    public String getId() { return id; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
    public TaskPriority getPriority() { return priority; }
    // Setters
    public void setStatus(TaskStatus status) { this.status = status; }
    @Override
    public int compareTo(Task other) {
        return this.priority.compareTo(other.priority);
    }
    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                '}';
    }
}
