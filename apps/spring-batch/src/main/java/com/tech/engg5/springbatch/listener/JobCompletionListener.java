package com.tech.engg5.springbatch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionListener implements JobExecutionListener {

  @Override
  public void beforeJob(JobExecution jobExecution) {
    LOG.info("Job Started: {}", jobExecution.getJobId());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    LOG.info("Job Ended: {}, Status: {}, Total Processed - {}", jobExecution.getJobId(), jobExecution.getStatus(),
      jobExecution.getStepExecutions().stream().mapToLong(StepExecution::getWriteCount).sum());
  }
}
