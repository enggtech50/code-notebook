# SpringBoot Reactive MongoDB Example POC

### Service Overview
This is a simple spring boot application that demonstrates how to use reactive mongodb with spring boot. It performs basic
CRUD operations on a mongodb collection.

### Tech Stack
- Java 17
- Spring Boot 
- Spring Data MongoDB Reactive
- Gradle
- JUnit 5

### Local Setup

To start the application, you need to have MongoDB running locally or provide a connection string to a remove MongoDB
instance. Use the docker command below to run if using Docker on local.

```shell
docker compose up -d
```

Clean build the application.

```shell
./gradlew clean build
```

### Postman cURL API Requests
Use the below mentioned postman cURL requests to test the application endpoints.

POST - Create a Employee Record
```
curl --location 'localhost:8080/employee/save' \
--header 'Content-Type: application/json' \
--data '{
	"employeeName": {
		"firstName": "Amit",
        "middleName": "Kumar",
		"lastName": "Gupta"
	},
	"jobTitle": "Assistant Manager",
	"jobFamily": "Support",
	"managementLevel": 8,
	"employeeSalary": 25000
}'
```

GET - Get all employee records
```
curl --location 'localhost:8080/employee/fetch-all'
```

GET - Get a employee record by employeeId
```
curl --location 'localhost:8080/employee/fetch/<employeeId>'
```

PUT - Update a employee record by employeeId
```
curl --location --request PUT 'localhost:8080/employee/update/<employeeId>' \
--header 'Content-Type: application/json' \
--data '{
    "employeeName": {
        "firstName": "Amit",
        "middleName": "Kumar",
        "lastName": "Gupta"
    },
    "jobTitle": "Assistant Manager",
    "jobFamily": "Management",
    "managementLevel": 8,
    "employeeSalary": 35000.0
}'
```

DELETE - Delete a employee record by employeeId
```
curl --location --request DELETE 'localhost:8080/employee/delete/<employeeId>'
```