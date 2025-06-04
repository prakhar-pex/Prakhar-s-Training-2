package NamePrefix;

import java.util.*;
import java.util.stream.Collectors;

public class PrefixStudents {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("John", "Male"),
                new Student("Alice", "Female"),
                new Student("Raj", "Male"),
                new Student("Priya", "Female")
        );

        List<String> prefixedNames = students.stream()
                .map(student -> {
                    String prefix = student.getGender().equalsIgnoreCase("Male") ? "Mr. " : "Ms. ";
                    return prefix + student.getName();
                })
                .collect(Collectors.toList());

        // Print names
        prefixedNames.forEach(System.out::println);
    }
}
