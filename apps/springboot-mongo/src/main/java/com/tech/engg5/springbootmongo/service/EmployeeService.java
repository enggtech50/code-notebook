package com.tech.engg5.springbootmongo.service;

import com.tech.engg5.springbootmongo.exception.DatabaseException;
import com.tech.engg5.springbootmongo.exception.ResourceNotFoundException;
import com.tech.engg5.springbootmongo.model.Employee;
import com.tech.engg5.springbootmongo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {

  EmployeeRepository employeeRepository;

  public Mono<Employee> createEmployeeRecord(Employee record) {
    LOG.info("Creating new employee record.");
    return employeeRepository.save(record)
      .onErrorResume(err -> {
        LOG.error("Failed to create employee record, employeeId - [{}]", record.getEmployeeId());
        return Mono.error(new DatabaseException("Database exception while saving the employee record.", err));
      });
  }

  public Flux<Employee> fetchAllEmployeeRecords() {
    LOG.info("Fetching all available employee records.");

    return employeeRepository.findAll()
      .switchIfEmpty(Flux.error(new ResourceNotFoundException("No employee records available.")));
  }

  public Mono<Employee> fetchEmployeeRecordByEmployeeId(String employeeId) {
    LOG.info("Fetching employee record by employeeId - [{}]", employeeId);
    return employeeRepository.findById(employeeId)
      .switchIfEmpty(Mono.error(new ResourceNotFoundException("No employee record found with employeeId - " + employeeId)));
  }

  public Mono<Employee> updateEmployeeRecordByEmployeeId(final String employeeId, Employee updatedRecord) {
    LOG.info("Updating employee record with employeeId - [{}]", employeeId);
    return employeeRepository.findById(employeeId)
      .switchIfEmpty(Mono.error(new ResourceNotFoundException("No employee record found with employeeId - " + employeeId +
        " to update.")))
      .flatMap(existingRecord -> {
        updatedRecord.setEmployeeId(employeeId);
        return employeeRepository.save(updatedRecord);
      });
  }

  public Mono<Void> deleteEmployeeRecordByEmployeeId(String employeeId) {
    LOG.info("Deleting employee record with employeeId - [{}]", employeeId);
    return employeeRepository.findById(employeeId)
      .switchIfEmpty(Mono.error(new ResourceNotFoundException("No employee record found with employeeId - " + employeeId +
        " to delete.")))
      .flatMap(employeeRepository::delete);
  }
}
