// package Jprofiler_Assignment_20thJune2025;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
    String name;
    int age;
    test(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
    public static void main(String[] args) {
        test person1 = new test("Prakhar", 22);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(()->{
            System.out.println("test");
            int []a = new int[200000000];
        });
        person1.displayInfo();
    }
}

