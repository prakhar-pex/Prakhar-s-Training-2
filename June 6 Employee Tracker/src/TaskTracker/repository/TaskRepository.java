package TaskTracker.repository;
import TaskTracker.exception.TaskNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class TaskRepository<T> {
    private List<T> taskList = new ArrayList<>();
    public void addTask(T task) {
        taskList.add(task);
    }
    public void removeTask(T task) throws TaskNotFoundException {
        if (!taskList.contains(task)) {
            throw new TaskNotFoundException("Task not found in repository");
        }
        taskList.remove(task);
    }
    public T findTaskById(String id) throws TaskNotFoundException {
        for (T task : taskList) {
            if (task instanceof TaskTracker.model.Task) {
                TaskTracker.model.Task t = (TaskTracker.model.Task) task;
                if (t.getId().equals(id)) {
                    return task;
                }
            }
        }
        throw new TaskNotFoundException("Task with ID " + id + " not found");
    }
    public List<T> getAllTasks() {
        return new ArrayList<>(taskList);
    }
}
