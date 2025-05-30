package com.tech.engg5.datamapper.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

  @XmlElement(name = "Employee_Id")
  String employeeId;

  @XmlElement(name = "Employee_Name")
  String employeeName;

  @XmlElement(name = "Job_Title")
  String jobTitle;

  @XmlElement(name = "Job_Family")
  String jobFamily;

  @XmlElement(name = "Management_Level")
  int managementLevel;

  @XmlElement(name = "Employee_Salary")
  double employeeSalary;
}
