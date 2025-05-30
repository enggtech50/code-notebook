package com.tech.engg5.datamapper.service;

import com.tech.engg5.datamapper.model.Employee;
import com.tech.engg5.datamapper.model.properties.DataFile;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class XmlDataService {

  private final DataFile dataFile;

  public XmlDataService(DataFile dataFile) {
    this.dataFile = dataFile;
  }

  public void marshalXMLObject() {
    try {
      var employee = Employee.builder()
        .employeeId("EMP001")
        .employeeName("John Doe")
        .jobTitle("Software Engineer")
        .jobFamily("Engineering")
        .managementLevel(11)
        .employeeSalary(75000.00)
        .build();

      JAXBContext jxctx = JAXBContext.newInstance(Employee.class);
      Marshaller marshaller = jxctx.createMarshaller();

      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      String filePath = dataFile.getPath();
      File outputDir = new File(filePath);

      if (!outputDir.exists()) {
        outputDir.mkdirs();
      }

      LOG.info("Marshalling java object: [{}]", employee.toString());

      File file = new File(outputDir, "employee-output.xml");

      marshaller.marshal(employee, System.out);
      marshaller.marshal(employee, file);

    } catch (Exception exc) {
      LOG.error("Error while marshalling XML object.");
      exc.printStackTrace();
    }
  }

  public void unmarshalXmlObject() {
    try {
      JAXBContext jxctx = JAXBContext.newInstance(Employee.class);
      Unmarshaller unmarshaller = jxctx.createUnmarshaller();

      String filePath = dataFile.getPath();
      File file = new File(filePath, "employee-output.xml");

      if (!file.exists()) {
        LOG.warn("File not found at: {}", file.getAbsolutePath());
        return;
      }

      var employee = (Employee) unmarshaller.unmarshal(file);
      LOG.info("Unmarshalled XML object to java object: [{}]", employee.toString());

    } catch (Exception exc) {
      LOG.error("Error while unmarshalling XML object.");
      exc.printStackTrace();
    }
  }
}
