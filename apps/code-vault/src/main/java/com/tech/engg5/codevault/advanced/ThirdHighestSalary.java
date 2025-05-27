package com.tech.engg5.codevault.advanced;

import com.tech.engg5.codevault.model.Employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// Find the third highest salary from a list of employees.

public class ThirdHighestSalary {

  public static void main(String[] args) {

    List<Employee> employees = Arrays.asList(
      new Employee("Alice", "London", 1000),
      new Employee("Bob", "USA", 1500),
      new Employee("John", "Ireland", 1600),
      new Employee("Harry", "USA", 1200),
      new Employee("Diana", "USA", 1500));

    try {
      double thirdHighestSalary = getThirdHighestSalary(employees);
      System.out.println("Third Highest Salary - " + thirdHighestSalary);
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  public static double getThirdHighestSalary(List<Employee> employees) {
    // Check if there are enough employees to have a third-highest salary
    // Change it according to the question.
    if (employees == null || employees.size() < 3) {
      throw new IllegalArgumentException("Not enough employees to find the third highest salary.");
    }

    // Get distinct salaries and sort them in descending order
    List<Double> result = employees.stream()
      .map(Employee::getSalary)
      .distinct()
      .sorted(Comparator.reverseOrder()).toList();

    // Check if there are at least three unique salaries
    if (result.size() < 3) {
      throw new IllegalArgumentException("Not enough distinct salaries to find the third highest.");
    }

    // Return the third-highest salary
    return result.get(2);
  }
}
