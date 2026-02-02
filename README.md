# 2026-DEV3-002
Development Books assignment

## Tech Stack
Java 17, Spring Boot, Maven, JUnit 5, MockMvc (integration testing)

## How to Run the Application

#### Make sure you have: 
Java 17 
& 
Maven 3.8+ 
installed 
```bash
java -version

mvn -version
```

## Build the Project

#### From the project root:
```bash
mvn clean install
```

#### Run the Application
```bash
mvn spring-boot:run
```

Application will start on:
```bash
http://localhost:8080
```
## API Usage
#### Endpoint
```bash
POST http://localhost:8080/api/v1/books/price-quotes
```

## Request Body
The API expects a JSON array of books.
```bash
[
  { "name": "CLEAN_CODE", "price": 50 },
  { "name": "CLEAN_CODER", "price": 50 }
]
```
## Response
```bash
95.0
```
(2 books × 50 = 100 → 5% discount → 95.0)

## Running Tests
#### Run all tests Integration tests included
```bash
mvn test
```

### Example Assignment Scenario
#### Input

2 × Clean Code

2 × Clean Coder

2 × Clean Architecture

1 × TDD by Example

1 × Legacy Code

#### Result
```bash
320.0
```





