package TaskTracker.model;

import java.util.Objects;


public class Employee {
    private String id;
    private String name;
    private String department;
    public Employee(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}