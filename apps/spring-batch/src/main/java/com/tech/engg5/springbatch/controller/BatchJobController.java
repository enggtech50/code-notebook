package com.tech.engg5.springbatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchJobController {

  private final JobLauncher jobLauncher;
  private final Job processEmployeeJob;

  @GetMapping("/trigger-job")
  public ResponseEntity<String> triggerJob() {
    try {
      JobParameters params = new JobParametersBuilder()
        .addLong("time", System.currentTimeMillis())
          .toJobParameters();

      JobExecution execution = jobLauncher.run(processEmployeeJob, params);
      return ResponseEntity.ok("Job executed with status: " + execution.getStatus());
    } catch (Exception exc) {
      return ResponseEntity.status(500).body("Job execution failed: " + exc.getMessage());
    }
  }
}
