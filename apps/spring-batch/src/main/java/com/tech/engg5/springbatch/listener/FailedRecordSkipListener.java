package com.tech.engg5.springbatch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FailedRecordSkipListener implements SkipListener<Object, Object> {

  @Value("${batch-processor.destination.output-path}")
  String basePath;

  @Override
  public void onSkipInRead(Throwable t) {
    writeToFile("READ ERROR", null, t);
  }

  @Override
  public void onSkipInWrite(Object item, Throwable t) {
    writeToFile("WRITE ERROR", item, t);
  }

  @Override
  public void onSkipInProcess(Object item, Throwable t) {
    writeToFile("PROCESS ERROR", item, t);
  }

  private void writeToFile(String phase, Object item, Throwable t) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(basePath + "/" + "event-processed-error.txt", true))) {
      writer.write(String.format("[%s]\nRecord: %s\nError: %s\n\n", phase, item, t.getMessage()));
    } catch (IOException e) {
      LOG.error("Could not log failure: {}", e.getMessage());
    }
  }
}
