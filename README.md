# Spring Boot Application

A basic Spring Boot application with a todo list web interface.

## Project Structure

```
springboot-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java (Main entry point)
│   │   │   └── controller/
│   │   │       └── HelloController.java (Web endpoints)
│   │   └── resources/
│   │       ├── application.properties (Configuration)
│   │       ├── templates/
│   │       │   └── home.html (Web interface)
│   │       └── static/
│   │           └── css/
│   │               └── style.css (Styling)
│   └── test/
│       └── java/com/example/demo/
└── pom.xml (Maven configuration)
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Features

- Todo list web interface
- Add tasks via web form
- In-memory task storage
- REST API status endpoint
- Thymeleaf templating
- CSS styling

## API Endpoints

- `GET /` - Main todo list interface
- `POST /addTask` - Add a new task
- `POST /greet` - Greeting endpoint (name, email)
- `GET /api/status` - Application status

## Dependencies

- Spring Boot Web Starter
- Spring Boot Thymeleaf Starter
- Spring Boot Test Starter
