# code-notebook
A centralized monorepo containing a collection of modular proof-of-concept (POC) services and technical implementations.
Each service demonstrates a focused concept—such as data transformation, messaging, persistence, or integration—built as
an independent module with its own configuration and isolated scope. Ideal for experimenting with backend patterns,
reusable components, and real-world microservice-inspired architectures.

### Features

- code-vault - Java sample programs
- vertx-mongo - Vert.x with MongoDB setup
- springboot-mongo - Spring Boot with MongoDB setup
- data-mapper - Data Mapping-Unmapping (Marshal-Unamrshal) setup
- kafka-consumer - Spring Kafka Consumer setup
- kafka-producer - Spring Kafka Producer setup
- spring-batch - Spring Batch application setup

### Github squash commits commands
Follow the below mentioned steps to squash the commits before merging PR to main branch. Change the parent branch name,
current branch name, commit message as per the requirement.

```
1. git status
2. git checkout <parent_branch_name>
3. git pull origin <parent_branch_name>
4. git checkout <current_sub-branch_name>
5. git reset --soft <parent_branch_name>
6. git status
7. git commit -m "<commit message>"
8. git push origin <current_sub-branch_name> -f
```

### Local Infrastructure Setup

- Wiremock standalone server
  - Download the standalone jar from the link [Download Wiremock Standalone Jar](https://repo1.maven.org/maven2/org/wiremock/wiremock-standalone/3.13.0/wiremock-standalone-3.13.0.jar).
    Use the below sample command to run the wiremock server. If using a different version of wiremock jar, change the version
    number in the command accordingly.

```
java -jar wiremock-standalone-3.3.1.jar --port 9999
```

- MongoDB local-instance & Kafka local-instance (Docker instance)
  - Use the docker-compose.yml file to pull the image & run the MongoDB instance locally on docker.
  - Use the docker-compose.yml file to pull the image & run the kafka-zookeeper and kafka-server locally on docker. Use the below command.

```
docker compose up -d
```

- Kafka local-instance (If not using Docker)
  - Alternatively, download the kafka binaries from [Download Apache Kafka](https://kafka.apache.org/downloads). Extract the files, then use the below mentioned commands to run the servers.

1. To start the zookeeper
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

2. To start the kafka server
```
bin/server-start.sh config/server.properties
```
3. To create the topic on server
```
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic <topic-name> --partitions 3 --replication-factor 1
```

- PostgreSQL local-instance (If not using Docker)
  - Download and install the PostgreSQL binaries from [Download PostgreSQL](https://www.postgresql.org/download/), depending on your machine.
