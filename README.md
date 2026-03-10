# Spring Boot Todo List Application

A modern, feature-rich todo list application built with Spring Boot 3.2.0 and Java 17.

## Features

### Core Functionality
- **Add Tasks** - Create new tasks with descriptions and optional due dates
- **Edit Tasks** - Inline editing with save/cancel functionality
- **Delete Tasks** - Remove tasks with instant action (no confirmation dialogs)
- **Complete/Incomplete** - Toggle task status with visual feedback
- **Due Date Management** - Date picker with past date validation
- **Overdue Detection** - Automatic highlighting of overdue incomplete tasks

### Technical Features
- **Modern UI** - Clean, responsive design with color-coded messages
- **Mobile Friendly** - Responsive layout that works on all devices
- **Input Validation** - Server-side and client-side validation
- **RESTful API** - JSON endpoint for application status
- **Clean Architecture** - Proper MVC separation and code organization

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Frontend**: Thymeleaf templating with HTML5/CSS3
- **Build Tool**: Maven 3.6+
- **Java Version**: Java 17
- **Database**: In-memory storage (ArrayList with AtomicLong ID generation)

## Project Structure

```
springboot-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java          # Main application entry point
│   │   │   ├── controller/
│   │   │   │   └── HelloController.java # REST endpoints and web controllers
│   │   │   └── model/
│   │   │       └── Task.java             # Task entity with id, description, completed, dueDate
│   │   └── resources/
│   │       ├── application.properties      # Application configuration
│   │       ├── static/
│   │       │   └── css/
│   │       │       └── style.css       # Modern CSS styling
│   │       └── templates/
│   │           └── home.html            # Main todo list interface
│   └── test/
│       └── java/com/example/demo/
│           └── DemoApplicationTests.java # Basic test setup
├── .gitignore                        # Git ignore rules
├── pom.xml                          # Maven configuration
└── README.md                         # This file
```

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Installation & Running
```bash
# Clone the repository
git clone https://github.com/smasher150/springboot-todo-app.git
cd springboot-todo-app

# Build the application
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/` | Main todo list interface |
| POST | `/addTask` | Add new task |
| POST | `/editTask` | Update existing task |
| POST | `/toggleTask` | Toggle task completion status |
| POST | `/deleteTask` | Delete task |
| GET | `/api/status` | Application health check |

## UI Features

### Message System
- **Success Messages** - Green background for successful operations
- **Error Messages** - Red background for validation errors  
- **Info Messages** - Blue background for general notifications

### Task Display
- **Completed Tasks** - Strikethrough styling with reduced opacity
- **Overdue Tasks** - Red highlighting for past-due incomplete tasks
- **Due Dates** - Clear date formatting and validation

### Interactive Elements
- **Inline Editing** - Click-to-edit with save/cancel options
- **Instant Delete** - Direct deletion without confirmation popups
- **Status Toggle** - One-click complete/incomplete switching

## Configuration

### Application Properties
- Server port: 8080
- Context path: /
- Logging: INFO level with DEBUG for demo package

### Date Validation
- Client-side: HTML5 date input with `min` attribute (today's date)
- Server-side: LocalDate validation preventing past dates
- Error handling: Clear user feedback for validation failures

## Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn clean test jacoco:report
```

## Future Enhancements

- [ ] Database persistence (H2/PostgreSQL)
- [ ] User authentication and authorization
- [ ] Task categories and tags
- [ ] Search and filter functionality
- [ ] Bulk operations (select all, delete completed)
- [ ] Task prioritization
- [ ] Export functionality (JSON/CSV)
- [ ] Real-time updates with WebSocket

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is open source and available under the MIT License.

## Author



*Demo application showcasing modern web development practices and clean code architecture.*
