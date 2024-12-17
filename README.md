
---

# Quiz Application

This project is a RESTful API for a quiz application with endpoints to start a quiz session, fetch questions, submit answers, and retrieve quiz results. The API is documented using **Swagger** and runs on **Java 11** with **Spring Boot**.

## Prerequisites

To run this application, ensure the following are installed:

1. **Java 11**  
   [Download Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or install it via a package manager like `sdkman`, `brew`, or `apt`.

2. **Maven**  
   Verify Maven installation:  
   ```bash
   mvn -v
   ```  
   If not installed, [download Maven](https://maven.apache.org/download.cgi).

## How to Run the Application

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd quiz-application
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application should now be running at:

```
http://localhost:8089
```

### 4. Access Swagger UI

Swagger documentation is available at:

```
http://localhost:8089/api/swagger-ui/index.html#
```

This interface allows you to test all available endpoints directly from the browser.

## Endpoints

### 1. Start a Quiz Session

**POST** `/quiz/start`  
Starts a new quiz session.

### 2. Get a Question

**GET** `/quiz/question`  
Fetches a random question for the given session.

### 3. Submit an Answer

**POST** `/quiz/submit`  
Submits an answer for the specified question.

### 4. Get Quiz Results

**GET** `/quiz/results`  
Fetches the results of the quiz session.

## Configuration

### Application Port

The default port is `8089`. To change it, modify `application.properties`:

**application.properties**

```yaml
server.port: 8089
```

### Swagger Configuration

Swagger UI is set up under the base path `/api/swagger-ui/index.html#`. No additional configuration is required unless you want to customize the API documentation.

## Build Commands

- **Clean and Install:**  
  ```bash
  mvn clean install
  ```

- **Run Tests:**  
  ```bash
  mvn test
  ```

## Dependencies

This project uses the following dependencies (specified in `pom.xml`):

- **Spring Boot Starter Web**: For building RESTful APIs.
- **Spring Boot Starter Test**: For unit and integration testing.
- **Springdoc OpenAPI UI**: For Swagger integration.


