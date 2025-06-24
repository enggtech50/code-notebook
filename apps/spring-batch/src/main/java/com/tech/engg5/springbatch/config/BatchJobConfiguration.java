package com.tech.engg5.springbatch.config;

import com.tech.engg5.springbatch.listener.FailedRecordSkipListener;
import com.tech.engg5.springbatch.listener.JobCompletionListener;
import com.tech.engg5.springbatch.model.EmployeeRaw;
import com.tech.engg5.springbatch.model.domain.CloudEvent;
import com.tech.engg5.springbatch.processor.CloudEventProcessor;
import com.tech.engg5.springbatch.reader.EmployeeItemReader;
import com.tech.engg5.springbatch.writer.CloudEventWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchJobConfiguration {

  @Bean
  public Job processEmployeeJob(JobRepository jobRepository, JobCompletionListener jobCompletionListener,
    Step employeeStep) {

    return new JobBuilder("processEmployeeJob", jobRepository)
      .incrementer(new RunIdIncrementer())
      .listener(jobCompletionListener)
      .flow(employeeStep)
      .end()
      .build();
  }

  @Bean
  public Step employeeStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
    CloudEventWriter cloudEventWriter, FailedRecordSkipListener failedRecordSkipListener) {

    return new StepBuilder("employeeStep", jobRepository)
      .<EmployeeRaw, CloudEvent>chunk(1, platformTransactionManager)
      .reader(employeeItemReader())
      .processor(cloudEventProcessor())
      .writer(cloudEventWriter)
      .faultTolerant()
      .skip(Exception.class)
      .skipLimit(100)
      .listener(failedRecordSkipListener)
      .build();
  }

  @Bean
  public EmployeeItemReader employeeItemReader() {
    return new EmployeeItemReader();
  }

  @Bean
  public CloudEventProcessor cloudEventProcessor() {
    return new CloudEventProcessor();
  }
}
