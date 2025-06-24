# Spring-Batch application Example POC

### Service Overview
This is a spring boot application that demonstrates file processing using Spring Batch. Below are the steps involved.
- Read the xml file line-by-line, and identify individual records.
- Deserialize the xml record to a Java object using Jackson library.
- Write the deserialized records to a success file.
- Write the records that failed to deserialize/processing to a failure file with failure reason and individual records.

Note - The xml files used for processing are located in `src/main/resources/input` directory. Change the xml files in this directory as per the requirement and change the model accordingly. The output files will be generated in `src/main/resources/output` directory.

### Tech Stack
- Java 17
- Spring Boot
- Spring Batch
- PostgreSQL (for storing batch metadata and job execution history, optional if not using a database for batch metadata storage)
- Gradle

### Local Setup
To setup the local environment, install the required PostgreSQL on your machine. Refer to the root README.md file for instructions.
After the installation, create a database and update the application.yml file with respective fields such as database name, username and password.
Copy and execute the content from `src/main/resources/schema-postgresql.sql` to create the required tables in the database.

Note - The tables are mandatory for the batch job and required by Spring-Batch to store the job execution history and other metadata.

Clean build the application.

```shell
./gradlew clean build
```