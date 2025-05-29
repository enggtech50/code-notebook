package com.tech.engg5.springbootmongo.controller;

import com.tech.engg5.springbootmongo.model.Employee;
import com.tech.engg5.springbootmongo.model.Name;
import com.tech.engg5.springbootmongo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
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
  @DisplayName("Verify that employee record is saved successfully.")
  void saveEmployeeRecord_success() {
    when(employeeService.createEmployeeRecord(sampleEmployee))
      .thenReturn(Mono.just(sampleEmployee));

    webTestClient.post()
      .uri("/employee/save")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(sampleEmployee)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Employee.class)
      .isEqualTo(sampleEmployee);
  }

  @Test
  @DisplayName("Verify that all employee records are fetched successfully.")
  void getAllEmployeeRecords_success() {
    when(employeeService.fetchAllEmployeeRecords())
      .thenReturn(Flux.just(sampleEmployee));

    webTestClient.get()
      .uri("/employee/fetch-all")
      .exchange()
      .expectStatus().isOk()
      .expectBodyList(Employee.class)
      .hasSize(1)
      .contains(sampleEmployee);
  }

  @Test
  @DisplayName("Verify that employee record is fetched by employeeId successfully.")
  void getEmployeeRecordByEmployeeId_success() {
    when(employeeService.fetchEmployeeRecordByEmployeeId("EMP001"))
      .thenReturn(Mono.just(sampleEmployee));

    webTestClient.get()
      .uri("/employee/fetch/EMP001")
      .exchange()
      .expectStatus().isOk()
      .expectBody(Employee.class)
      .isEqualTo(sampleEmployee);
  }

  @Test
  @DisplayName("Verify that employee record is updated successfully.")
  void updateEmployeeRecord_success() {
    Employee updated = new Employee();
    updated.setEmployeeId("EMP001");
    updated.setJobFamily("Engineering SME");
    updated.setJobTitle("Senior Software Engineer");

    when(employeeService.updateEmployeeRecordByEmployeeId("EMP001", updated))
      .thenReturn(Mono.just(updated));

    webTestClient.put()
      .uri("/employee/update/EMP001")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(updated)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Employee.class)
      .isEqualTo(updated);
  }

  @Test
  @DisplayName("Verify that employee record is deleted successfully.")
  void deleteEmployeeRecord_success() {
    when(employeeService.deleteEmployeeRecordByEmployeeId("EMP001"))
      .thenReturn(Mono.empty());

    webTestClient.delete()
      .uri("/employee/delete/EMP001")
      .exchange()
      .expectStatus().isOk()
      .expectBody(Void.class)
      .isEqualTo(null);
  }
}
