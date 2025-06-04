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

### Local Setup

To start the application, refer the root README.md file for instructions.

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

### Postman cURL API Requests
Use the below mentioned postman cURL requests to test the application endpoints.

POST - Create a Product
```
curl --location 'http://localhost:8888/products' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Mobile",
    "price": 999.89
}'
```

Get - Get all Products
```
curl --location 'http://localhost:8888/products'
```

GET - Get a Product by ID
```
curl --location 'http://localhost:8888/products/<id>'
```

UPDATE - Update a Product by ID
```
curl --location --request PUT 'http://localhost:8888/products/<id>' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Mobile",
    "price": 564.23
}'
```

DELETE - Delete a Product by ID
```
curl --location --request DELETE 'http://localhost:8888/products/<id>'
```