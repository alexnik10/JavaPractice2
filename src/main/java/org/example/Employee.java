package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Employee {
    private String fullName;
    private Integer age;
    private String department;
    private Double salary;

    public Employee(String fullName, Integer age, String department, Double salary) {
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "{" +
                "ФИО='" + fullName + '\'' +
                ", возраст=" + age +
                ", отдел='" + department + '\'' +
                ", зарплата=" + salary +
                '}';
    }

    public static void execute() {
        System.out.print("\n    Задание 3.\n");
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иванов Иван Иванович", 35, "ИТ", 600000.0));
        employees.add(new Employee("Петров Леонид Евгеньевич", 42, "Финансы", 97000.0));
        employees.add(new Employee("Васильев Сидор Михайлович", 29, "Маркетинг", 55000.0));
        employees.add(new Employee("Кузнецова Анна Павловна", 30, "ИТ", 80000.0));
        employees.add(new Employee("Смирнова Ольга Васильевна", 25, "Продажи", 50000.0));

        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .collect(Collectors.toList());
        System.out.println("Сотрудники, отсортированные по зарплате:");
        sortedEmployees.forEach(System.out::println);
    }
}
