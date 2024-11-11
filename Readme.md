
# <p align = center> Online Quiz System </p>

## Project Overview

The **Online Quiz System** is a web application designed for users to participate in quizzes with dynamic question sets, track their scores, and receive comprehensive reports on their performance. Built for educational and professional settings, this platform emphasizes ease of use, real-time feedback, and detailed tracking. With its robust architecture, the system can handle multiple users and a constantly updated question bank.

### Project Goals
1. **User-Friendly Interface**: Provide seamless registration, login, and quiz access.
2. **Dynamic Question Management**: Allow admins to add, edit, and delete questions.
3. **Automated Scoring**: Deliver immediate feedback and learning insights.
4. **Detailed Reporting**: Track user progress over time, highlighting strengths and improvement areas.
5. **Logging and Error Management**: Centralized tracking of user actions and error handling for smooth operation.

---

## Features

- **Dynamic Question Bank**: The question bank can be managed by admin users who can add, update, or remove questions to keep quizzes relevant.
- **User Authentication**: Secure user registration and login functionality ensures only registered users can access quizzes.
- **Real-Time Scoring**: Immediate feedback on quiz completion allows users to learn from mistakes.
- **Comprehensive Reporting**: Users can view detailed reports on their performance, tracking progress over time.
- **Error Logging**: The system logs user actions, quiz attempts, and errors to monitor activities and maintain stability.
- **Testing**: Includes both unit and integration tests using JUnit, ensuring functionality and reliability.

---

## Application Architecture

The **Online Quiz System** is built using a three-tier **MVC (Model-View-Controller)** architecture. This design pattern ensures a clean separation of concerns and allows easy scalability and maintainability.

### Architectural Layers
1. **Controllers**: This layer handles all incoming HTTP requests. Each request is routed to the appropriate service, and responses are structured in JSON. Key controllers include:
   - **UserController**: Manages user registration, login, and profile.
   - **QuizController**: Manages quiz participation, question retrieval, and answer submission.
   - **OnlineQuizSystemController**: Offers general controls, such as health checks and system configurations.
   
2. **Services**: This layer contains business logic and coordinates data processing. Service classes include:
   - **UserService**: Manages user-related operations, including authentication and session handling.
   - **QuizService**: Controls quiz-related logic, such as scoring, question selection, and performance tracking.
   - **OnlineQuizSystemService**: Manages overarching application functions and coordinates between other services as required.

3. **Repositories**: These classes manage data storage and retrieval using Spring Data JPA. Key repositories include:
   - **UserRepository**: Handles CRUD operations for user data.
   - **QuizAttemptRepository**: Stores each quiz attempt and associated data.
   - **UserAnswerRepository**: Logs each answer submitted by users.
   - **OnlineQuizSystemRepository**: Manages application-wide settings and configurations.

4. **Entities**: Represent database tables, defining key data structures for the system. Entity classes include:
   - **User**: Contains user data such as ID, username, password, role, etc.
   - **QuizAttempt**: Logs each attempt with the score, time, and user ID.
   - **UserAnswer**: Tracks each answer provided by the user, indicating correctness.
   - **Question**: Stores question text, multiple-choice options, and correct answers.

---

## Tech Stack

This system is developed using industry-standard tools for a stable and scalable backend architecture:

- **Java**: A robust programming language that offers stability and scalability.
- **Spring Boot**: Provides a framework for quick setup and development, along with dependency management.
- **Spring MVC**: Defines the MVC structure for managing controllers and views.
- **Spring Data JPA**: Simplifies database interaction, handling common CRUD operations.
- **Spring Security**: Provides authentication and authorization to secure the application.
- **MySQL**: A relational database that stores user data, quiz questions, and results.
- **Maven**: Manages dependencies and builds, ensuring efficient development.
- **JUnit and Mockito**: Enables unit and integration testing for robust functionality.
- **SLF4J/Logback**: Provides flexible logging configurations for tracking application behavior.
- **Spring Boot Actuator**: Monitors system health, uptime, and error rates.

---

## Database Schema

The system's database schema is optimized for managing quizzes, storing user performance data, and logging quiz attempts.

- **User Table**: Stores user credentials and roles, such as username, password (encrypted), email, and role (e.g., Admin or User).
- **Question Table**: Holds quiz questions, each with a unique ID, question text, multiple-choice options, and the correct answer.
- **Quiz_Attempt Table**: Records each quiz attempt, storing the user ID, score, and timestamps for the attempt's start and end.
- **User_Answer Table**: Tracks the answers provided by users, with fields for question ID, selected answer, correctness, and attempt ID.

---

## Project Structure

The project directory is organized to ensure a clear separation of concerns:

Directory map:

```plaintext
OnlineQuizSystem/
├── .idea/                                  # IntelliJ IDEA project files
│   ├── compiler.xml
│   ├── dbnavigator.xml
│   ├── encodings.xml
│   ├── jarRepositories.xml
│   ├── misc.xml
│   ├── workspace.xml
│   └── .gitignore
├── .mvn/                                   # Maven wrapper files
│   └── wrapper/
│       ├── maven-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/com/incture/OnlineQuizSystem/
│   │   │   ├── Controller/                 # Controller classes for HTTP request handling
│   │   │   │   ├── OnlineQuizSystemController.java
│   │   │   │   ├── QuizController.java
│   │   │   │   └── UserController.java
│   │   │   ├── Entity/                     # Data model classes mapped to database tables
│   │   │   │   ├── OnlineQuizSystem.java
│   │   │   │   ├── QuizAttempt.java
│   │   │   │   ├── User.java
│   │   │   │   └── UserAnswer.java
│   │   │   ├── Exception/                  # Custom exception classes for error handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── InvalidAnswerException.java
│   │   │   │   └── QuizNotFoundException.java
│   │   │   ├── Repository/                 # Interfaces for database CRUD operations
│   │   │   │   ├── OnlineQuizSystemRepository.java
│   │   │   │   ├── QuizAttemptRepository.java
│   │   │   │   ├── UserAnswerRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── Service/                    # Business logic for the application
│   │   │   │   ├── OnlineQuizSystemService.java
│   │   │   │   ├── QuizService.java
│   │   │   │   └── UserService.java
│   │   │   ├── OnlineQuizSystemApplication.java   # Main entry point of the application
│   │   ├── resources/
│   │   │   ├── application.properties      # Configuration file for database, logging, etc.
│   │   │   ├── templates/                  # Placeholder for HTML templates (if applicable)
│   │   │   └── static/                     # Placeholder for static resources (CSS, JS, images)
│   ├── test/
│   │   ├── java/com/incture/OnlineQuizSystem/
│   │   │   ├── Controller/                 # Controller layer unit and integration tests
│   │   │   │   ├── OnlineQuizSystemControllerTest.java
│   │   │   │   ├── QuizControllerTest.java
│   │   │   │   └── UserControllerTest.java
│   │   │   ├── Service/                    # Service layer unit tests
│   │   │   │   ├── OnlineQuizSystemServiceTest.java
│   │   │   │   ├── QuizServiceTest.java
│   │   │   │   └── UserServiceTest.java
│   │   │   └── resources/                  # Test resources (if applicable)
├── target/                                 # Compiled output and generated resources
│   ├── classes/                            # Compiled application classes
│   │   ├── application.properties
│   │   └── com/incture/OnlineQuizSystem/
│   │       ├── Controller/
│   │       ├── Entity/
│   │       ├── Exception/
│   │       ├── Repository/
│   │       └── Service/
│   ├── test-classes/                       # Compiled test classes
│   │   └── com/incture/OnlineQuizSystem/
│   │       ├── Controller/
│   │       └── Service/
├── HELP.md                                 # Optional help file for the project
├── mvnw                                    # Maven wrapper script for Linux/macOS
├── mvnw.cmd                                # Maven wrapper script for Windows
├── pom.xml                                 # Maven Project Object Model (POM) file
├── .classpath                              # Eclipse configuration file
├── .project                                # Eclipse project file
└── .gitignore                              # Git ignore file

```


### Detailed Breakdown
- **Controller Layer**: Defines endpoints for user interactions, quiz management, and data retrieval.
- **Service Layer**: Performs data manipulation and enforces business rules.
- **Repository Layer**: Manages database CRUD operations with Spring Data JPA.
- **Entity Layer**: Maps Java objects to database tables, simplifying data handling.
- **Exception Layer**: Contains custom exceptions for robust error handling and user-friendly feedback.

---

## Logging and Error Handling

**Logging**: SLF4J/Logback is used for application-level logging. Each action and error is logged with details such as timestamp, severity, and stack trace for error cases. Logs provide a trail of actions for auditing and debugging purposes.

**Error Handling**: A global exception handler is implemented, ensuring all exceptions are uniformly managed. Sensitive information is hidden from users, while descriptive messages are logged for troubleshooting. 

---

## Testing

Testing is a core component of the Online Quiz System to ensure each module functions as intended.

1. **Unit Testing**: Each function is tested in isolation to verify functionality. JUnit and Mockito are used to mock dependencies and test edge cases, covering critical features like user authentication and quiz scoring.
2. **Integration Testing**: Verifies the interaction between modules, simulating real-world use cases. `MockMvc` is used to mimic HTTP requests, ensuring controllers, services, and repositories work seamlessly.

---

## Key API Endpoints

- **User Registration**: `POST /api/users/register`  
  Registers a new user with required details (username, password, email).

- **User Login**: `POST /api/users/login`  
  Authenticates a user and grants access to quizzes.

- **Start Quiz**: `POST /api/quiz/startQuiz/{quizId}?userId={userId}`  
  Begins a quiz attempt for a given user and quiz ID.

- **Submit Answer**: `POST /api/quiz/{quizId}/attempt/{attemptId}/submitAnswer`  
  Submits an answer for a specific question in an active quiz attempt.

- **Complete Quiz**: `POST /api/quiz/{quizId}/attempt/{attemptId}/complete`  
  Finalizes the quiz attempt, calculates the score, and records the result.

- **Retrieve Quiz History**: `GET /api/quiz/user/{userId}/history`  
  Retrieves a user’s past quiz attempts and scores.

- **Performance Report**: `GET /api/quiz/user/{userId}/performanceReport`  
  Provides a detailed performance report for the user’s progress over time.

---

## Future Enhancements

Potential future features include:
- Adding time-based quizzes.
- Expanding reporting capabilities with graphical insights.
- Implementing more complex question types (e.g., true/false, image-based).
