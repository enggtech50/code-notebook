package com.tech.engg5.springbootmongo.controller;

import com.tech.engg5.springbootmongo.model.Employee;
import com.tech.engg5.springbootmongo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/employee", produces = APPLICATION_JSON_VALUE)
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @PostMapping("/save")
  public Mono<Employee> saveEmployeeRecord(@RequestBody Employee record) {
    LOG.info("Received request to save a new employee record");
    return employeeService.createEmployeeRecord(record);
  }

  @GetMapping("/fetch-all")
  public Flux<Employee> getAllEmployeeRecords() {
    LOG.info("Received request to view all employee records.");
    return employeeService.fetchAllEmployeeRecords();
  }

  @GetMapping("/fetch/{employeeId}")
  public Mono<Employee> getEmployeeRecordByEmployeeI(@PathVariable String employeeId) {
    LOG.info("Received request to view employee record by employeeId.");
    return employeeService.fetchEmployeeRecordByEmployeeId(employeeId);
  }

  @PutMapping("/update/{employeeId}")
  public Mono<Employee> updateEmployeeRecord(@RequestBody Employee updatedRecord, @PathVariable String employeeId) {
    LOG.info("Received request to update employee record.");
    return employeeService.updateEmployeeRecordByEmployeeId(employeeId, updatedRecord);
  }

  @DeleteMapping("/delete/{employeeId}")
  public Mono<Void> deleteEmployeeRecord(@PathVariable String employeeId) {
    LOG.info("Received request to delete the employee record with employeeId - [{}]", employeeId);
    return employeeService.deleteEmployeeRecordByEmployeeId(employeeId);
  }
}
