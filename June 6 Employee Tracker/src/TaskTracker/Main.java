package TaskTracker;


import TaskTracker.model.*;
        import TaskTracker.service.TaskManager;
import TaskTracker.service.TaskMonitor;
import TaskTracker.util.DateUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        TaskMonitor taskMonitor = new TaskMonitor(taskManager);
        taskMonitor.start();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\nEmployee Task Tracker System");
            System.out.println("1. Add Employee");
            System.out.println("2. Assign Task");
            System.out.println("3. View Employee Tasks");
            System.out.println("4. Update Task Status");
            System.out.println("5. View All Tasks");
            System.out.println("6. Search Tasks by Keyword");
            System.out.println("7. View Tasks Due Tomorrow");
            System.out.println("8. View Employees with Many Pending Tasks");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addEmployee(scanner, taskManager);
                        break;
                    case 2:
                        assignTask(scanner, taskManager);
                        break;
                    case 3:
                        viewEmployeeTasks(scanner, taskManager);
                        break;
                    case 4:
                        updateTaskStatus(scanner, taskManager);
                        break;
                    case 5:
                        viewAllTasks(taskManager);
                        break;
                    case 6:
                        searchTasksByKeyword(scanner, taskManager);
                        break;
                    case 7:
                        viewTasksDueTomorrow(taskManager);
                        break;
                    case 8:
                        viewEmployeesWithManyPendingTasks(scanner, taskManager);
                        break;
                    case 9:
                        running = false;
                        taskMonitor.stopMonitoring();
                        System.out.println("Exiting system...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static void addEmployee(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        Employee employee = new Employee(id, name, department);
        System.out.println("Employee added: " + employee);
    }
    private static void assignTask(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter employee ID: ");
        String empId = scanner.nextLine();
        Employee employee = new Employee(empId, "", ""); // Simplified for demo
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        System.out.print("Enter due date (yyyy-MM-dd): ");
        LocalDate dueDate = DateUtils.parseDate(scanner.nextLine());
        System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");
        TaskPriority priority = TaskPriority.valueOf(scanner.nextLine().toUpperCase());
        Task task = new Task(taskId, description, TaskStatus.PENDING, dueDate, priority);
        taskManager.assignTask(employee, task);
        System.out.println("Task assigned successfully: " + task);
    }
    private static void viewEmployeeTasks(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter employee ID: ");
        String empId = scanner.nextLine();
        Employee employee = new Employee(empId, "", ""); // Simplified for demo
        List<Task> tasks = taskManager.getEmployeeTasks(employee);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for this employee.");
        } else {
            System.out.println("Tasks for employee " + empId + ":");
            tasks.forEach(System.out::println);
        }
    }
    private static void updateTaskStatus(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine();
        System.out.print("Enter new status (PENDING, IN_PROGRESS, COMPLETED): ");
        TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());
        try {
            taskManager.updateTaskStatus(taskId, status);
            System.out.println("Task status updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }
    private static void viewAllTasks(TaskManager taskManager) {
        List<Task> tasks = taskManager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("All Tasks:");
            tasks.forEach(System.out::println);
        }
    }
    private static void searchTasksByKeyword(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();
        List<Task> tasks = taskManager.getTasksByKeyword(keyword);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found with the keyword: " + keyword);
        } else {
            System.out.println("Tasks containing '" + keyword + "':");
            tasks.forEach(System.out::println);
        }
    }
    private static void viewTasksDueTomorrow(TaskManager taskManager) {
        List<Task> tasks = taskManager.getTasksDueTomorrow();
        if (tasks.isEmpty()) {
            System.out.println("No tasks due tomorrow.");
        } else {
            System.out.println("Tasks due tomorrow:");
            tasks.forEach(System.out::println);
        }
    }
    private static void viewEmployeesWithManyPendingTasks(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter minimum number of pending tasks to filter: ");
        int threshold = Integer.parseInt(scanner.nextLine());
        List<Employee> employees = taskManager.getEmployeesWithManyPendingTasks(threshold);
        if (employees.isEmpty()) {
            System.out.println("No employees found with more than " + threshold + " pending tasks.");
        } else {
            System.out.println("Employees with more than " + threshold + " pending tasks:");
            employees.forEach(System.out::println);
        }
    }
}
