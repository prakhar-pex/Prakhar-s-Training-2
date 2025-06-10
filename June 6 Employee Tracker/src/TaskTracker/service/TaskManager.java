package TaskTracker.service;
import TaskTracker.exception.TaskNotFoundException;
import TaskTracker.model.Employee;
import TaskTracker.model.Task;
import TaskTracker.model.TaskPriority;
import TaskTracker.model.TaskStatus;
import TaskTracker.repository.TaskRepository;
import java.time.LocalDate;
import java.util.*;
        import java.util.stream.Collectors;


public class TaskManager {
    private final Map<Employee, List<Task>> employeeTasks = new HashMap<>();
    private final TaskRepository<Task> taskRepository = new TaskRepository<>();
    public void assignTask(Employee employee, Task task) {
        employeeTasks.computeIfAbsent(employee, k -> new ArrayList<>()).add(task);
        taskRepository.addTask(task);
    }
    public List<Task> getEmployeeTasks(Employee employee) {
        return employeeTasks.getOrDefault(employee, new ArrayList<>());
    }
    public void updateTaskStatus(String taskId, TaskStatus newStatus) throws TaskNotFoundException {
        Task task = taskRepository.findTaskById(taskId);
        task.setStatus(newStatus);
    }
    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }
    public List<Task> getTasksByKeyword(String keyword) {
        return taskRepository.getAllTasks().stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Task> getTasksDueTomorrow() {
        return taskRepository.getAllTasks().stream()
                .filter(task -> task.getDueDate().equals(LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());
    }
    public List<Employee> getEmployeesWithManyPendingTasks(int threshold) {
        return employeeTasks.entrySet().stream()
                .filter(entry -> {
                    long pendingCount = entry.getValue().stream()
                            .filter(task -> task.getStatus() == TaskStatus.PENDING)
                            .count();
                    return pendingCount > threshold;
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    public List<Task> getOverdueTasks() {
        return taskRepository.getAllTasks().stream()
                .filter(task -> task.getStatus() != TaskStatus.COMPLETED)
                .filter(task -> task.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }
    public void sortTasksByPriority(List<Task> tasks) {
        tasks.sort(Comparator.naturalOrder());
    }
    public void sortTasksByDueDate(List<Task> tasks) {
        tasks.sort(Comparator.comparing(Task::getDueDate));
    }
}
