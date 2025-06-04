# Kafka Producer Example POC

### Service Overview
This service demonstrates a kafka producer that push messages to a kafka topic via a rest endpoint.

### Tech Stack
- Java 17
- Spring Boot
- Spring Kafka
- Gradle

Clean build the application.

```shell
./gradlew clean build
```

To run the application, add the below configuration to the main application run configuration.
```
-Dspring.profiles.active=local
```