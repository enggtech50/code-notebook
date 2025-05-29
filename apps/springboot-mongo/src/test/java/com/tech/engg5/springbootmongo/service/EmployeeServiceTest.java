package com.tech.engg5.springbootmongo.service;

import com.tech.engg5.springbootmongo.exception.DatabaseException;
import com.tech.engg5.springbootmongo.exception.ResourceNotFoundException;
import com.tech.engg5.springbootmongo.model.Employee;
import com.tech.engg5.springbootmongo.model.Name;
import com.tech.engg5.springbootmongo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeService employeeService;

  private Employee sampleEmployee;

  @BeforeEach
  void setup() {
    sampleEmployee = new Employee();
    sampleEmployee.setEmployeeId("EMP001");
    sampleEmployee.setEmployeeName(Name.builder().firstName("John").middleName("K").lastName("Doe").build());
    sampleEmployee.setJobTitle("Software Engineer");
    sampleEmployee.setJobFamily("Engineering");
    sampleEmployee.setManagementLevel(4);
    sampleEmployee.setEmployeeSalary(75000.00);
  }

  @Test
  @DisplayName("Verify Employee Record Creation - Success")
  void createEmployeeRecord_success() {
    when(employeeRepository.save(sampleEmployee)).thenReturn(Mono.just(sampleEmployee));

    StepVerifier.create(employeeService.createEmployeeRecord(sampleEmployee))
      .expectNext(sampleEmployee)
      .verifyComplete();
  }

  @Test
  @DisplayName("Verify Employee Record Creation - Database Error")
  void createEmployeeRecord_databaseError() {
    when(employeeRepository.save(sampleEmployee))
      .thenReturn(Mono.error(new RuntimeException("DB error")));

    StepVerifier.create(employeeService.createEmployeeRecord(sampleEmployee))
      .expectError(DatabaseException.class)
      .verify();
  }

  @Test
  @DisplayName("Verify Employee Record Creation - Success")
  void fetchAllEmployeeRecords_success() {
    when(employeeRepository.findAll()).thenReturn(Flux.just(sampleEmployee));

    StepVerifier.create(employeeService.fetchAllEmployeeRecords())
      .expectNext(sampleEmployee)
      .verifyComplete();
  }

  @Test
  @DisplayName("Verify Employee Record Creation - Resource Not Found Error")
  void fetchAllEmployeeRecords_empty() {
    when(employeeRepository.findAll()).thenReturn(Flux.empty());

    StepVerifier.create(employeeService.fetchAllEmployeeRecords())
      .expectError(ResourceNotFoundException.class)
      .verify();
  }

  @Test
  @DisplayName("Verify Fetch Employee Record by ID - Success")
  void fetchEmployeeRecordById_success() {
    when(employeeRepository.findById("EMP001")).thenReturn(Mono.just(sampleEmployee));

    StepVerifier.create(employeeService.fetchEmployeeRecordByEmployeeId("EMP001"))
      .expectNext(sampleEmployee)
      .verifyComplete();
  }

  @Test
  @DisplayName("Verify Fetch Employee Record by ID - Resource Not Found Error")
  void fetchEmployeeRecordById_notFound() {
    when(employeeRepository.findById("EMP001")).thenReturn(Mono.empty());

    StepVerifier.create(employeeService.fetchEmployeeRecordByEmployeeId("EMP001"))
      .expectError(ResourceNotFoundException.class)
      .verify();
  }

  @Test
  @DisplayName("Verify Update Employee Record - Success")
  void updateEmployeeRecord_success() {
    Employee updated = new Employee();
    updated.setJobTitle("Sr. Software Engineer");

    when(employeeRepository.findById("EMP001")).thenReturn(Mono.just(sampleEmployee));
    when(employeeRepository.save(any(Employee.class)))
      .thenReturn(Mono.just(updated));

    StepVerifier.create(employeeService.updateEmployeeRecordByEmployeeId("EMP001", updated))
      .expectNext(updated)
      .verifyComplete();
  }

  @Test
  @DisplayName("Verify Update Employee Record - Resource Not Found Error")
  void updateEmployeeRecord_notFound() {
    when(employeeRepository.findById("EMP001")).thenReturn(Mono.empty());

    StepVerifier.create(employeeService.updateEmployeeRecordByEmployeeId("EMP001", sampleEmployee))
      .expectError(ResourceNotFoundException.class)
      .verify();
  }

  @Test
  @DisplayName("Verify Delete Employee Record - Success")
  void deleteEmployeeRecord_success() {
    when(employeeRepository.findById("EMP001")).thenReturn(Mono.just(sampleEmployee));
    when(employeeRepository.delete(sampleEmployee)).thenReturn(Mono.empty());

    StepVerifier.create(employeeService.deleteEmployeeRecordByEmployeeId("EMP001"))
      .verifyComplete();
  }

  @Test
  @DisplayName("Verify Delete Employee Record - Resource Not Found Error")
  void deleteEmployeeRecord_notFound() {
    when(employeeRepository.findById("EMP001")).thenReturn(Mono.empty());

    StepVerifier.create(employeeService.deleteEmployeeRecordByEmployeeId("EMP001"))
      .expectError(ResourceNotFoundException.class)
      .verify();
  }
}
