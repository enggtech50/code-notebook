# Vert.x MongoDB Example POC

### Service Overview
This is a simple Vert.x application that demonstrates how to use MongoDB with Vert.x. It provides basic CRUD operations
on a MongoDB collection.

### Tech Stack
- Java 17
- Vert.x
- MongoDB
- Gradle
- JUnit 5

To start the application, you need to have MongoDB running locally or provide a connection string to a remove MongoDB
instance. Use the docker command below to run if using Docker on local.

```shell
docker compose up -d
```

Clean build the application.

```shell
./gradlew clean build
```

Run the application on local.

```shell
./gradlew :apps:vertx-mongo:run
```

Run all the tests.
```shell
./gradlew :apps:vertx-mongo:test
```