package com.tech.engg5.springbatch.reader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tech.engg5.springbatch.model.EmployeeRaw;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeItemReader implements ItemReader<EmployeeRaw>, ItemStream {

  @Value("${batch-processor.source.input-path}")
  String basePath;

  FlatFileItemReader<String> delegateReader;
  XmlMapper xmlMapper = new XmlMapper();
  boolean recordComplete;
  EmployeeRaw employee;
  StringBuilder stringBuilder;

  @Override
  public EmployeeRaw read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    recordComplete = false;
    while (!recordComplete) {
      processFile(delegateReader.read());
    }
    EmployeeRaw record = employee;
    employee = null;
    return record;
  }

  @SneakyThrows
  public void processFile(String line) {
    if (line == null) {
      recordComplete = true;
      employee = null;
      stringBuilder = null;
      return;
    }

    if ("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Employee_Payload>".equalsIgnoreCase(line)) {
      stringBuilder = new StringBuilder(line);
    } else if("</Employee_Payload>".equalsIgnoreCase(line)){
      stringBuilder.append(line);
      employee = xmlMapper.readValue(stringBuilder.toString(), EmployeeRaw.class);
      recordComplete = true;
    } else {
      stringBuilder.append(line);
    }
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    try {
      Path path = Paths.get(basePath, "employee-payloads-invalid.txt").toAbsolutePath();
      FileSystemResource resource = new FileSystemResource(path.toFile());

      this.delegateReader = new FlatFileItemReaderBuilder<String>()
        .name("employeeReader")
        .resource(resource)
        .lineMapper(new PassThroughLineMapper())
        .build();
      this.delegateReader.open(executionContext);
    } catch (Exception exc) {
      throw new ItemStreamException("Failed to initialize the reader", exc);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    this.delegateReader.update(executionContext);
  }

  @Override
  public void close() throws ItemStreamException {
    this.delegateReader.close();
  }
}
