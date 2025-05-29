package com.tech.engg5.springbootmongo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "employee")
public class Employee {

  @Id
  String employeeId;
  Name employeeName;
  String jobTitle;
  String jobFamily;
  int managementLevel;
  double employeeSalary;
}
