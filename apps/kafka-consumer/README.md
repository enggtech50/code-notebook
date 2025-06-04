# Kafka Consumer Example POC

### Service Overview
This service demonstrates a kafka consumer that consumes the messages from a kafka topic using reactive kafka library.

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