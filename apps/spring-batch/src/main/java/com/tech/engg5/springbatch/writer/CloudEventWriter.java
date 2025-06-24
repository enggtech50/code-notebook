package com.tech.engg5.springbatch.writer;

import com.tech.engg5.springbatch.model.domain.CloudEvent;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CloudEventWriter implements ItemWriter<CloudEvent> {

  @Value("${batch-processor.destination.output-path}")
  String basePath;

  @Override
  public void write(Chunk<? extends CloudEvent> chunk) throws Exception {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(basePath + "/" + "processed-success.txt", true))) {
      for (CloudEvent event : chunk) {
        writer.write(event.toString());
        writer.newLine();
      }
      LOG.info("Successfully written {} events.", chunk.size());
    } catch (Exception exc) {
      LOG.error("Error writing chunk to file: {}", exc.getMessage(), exc);
      throw new RuntimeException("Error writing chunk to file: " + exc.getMessage(), exc);
    }
  }
}
