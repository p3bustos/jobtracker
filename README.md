# Job Application Tracker

A microservice-based REST API for tracking job applications, built with Java 17 and Spring Boot. This project demonstrates modern software engineering practices including design patterns, test-driven development, and RESTful API design.

[![Build Status](https://github.com/p3bustos/jobtracker/actions/workflows/jobtracker.yml/badge.svg)](https://github.com/p3bustos/jobtracker/actions/workflows/jobtracker.yml)
## 🎯 Project Overview

This application provides a system for managing job applications with full CRUD operations, status tracking, and analytics. Built as a learning project to demonstrate enterprise Java development skills and best practices.

## 🛠️ Technologies & Skills Demonstrated

### Core Technologies
- **Java 17** - Modern Java with records, enhanced switch expressions
- **Spring Boot 3.2** - Microservices framework
- **Spring Data JPA** - ORM and data access layer
- **H2 Database** - Embedded database for development
- **Maven** - Dependency management and build tool

### Software Engineering Best Practices
- **Design Patterns**
  - Repository Pattern (data access abstraction)
  - Service Pattern (business logic layer)
  - DTO Pattern (API/domain separation)
  - Factory Pattern (DTO mapping)
- **REST API Design** - RESTful endpoints with proper HTTP methods and status codes
- **JUnit 5 Testing** - 11 comprehensive unit tests with Mockito
- **Exception Handling** - Global exception handler with proper error responses
- **Bean Validation** - Request validation with Jakarta Validation
- **CI/CD** - GitHub Actions pipeline for automated build and testing

### Architecture Highlights
- **Layered Architecture** - Clear separation of concerns (Controller → Service → Repository)
- **Dependency Injection** - Constructor-based injection throughout
- **OOP Principles** - Encapsulation, inheritance, polymorphism demonstrated
- **Data Structures** - Effective use of Java Collections, Streams API, enums

## 📋 Features

- ✅ Create, read, update, delete job applications
- ✅ Track application status through hiring pipeline
- ✅ Filter applications by status
- ✅ View active applications and interview pipeline
- ✅ Analytics and statistics dashboard
- ✅ Comprehensive error handling
- ✅ OpenAPI/Swagger documentation
- ✅ Automated testing with CI/CD

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository
```bash
git clone https://github.com/p3bustos/jobtracker.git
cd jobtracker
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

# Deploy
render deploy
```

**Render Configuration:**
- **Environment:** Docker
- **Plan:** Free (750 hours/month)
- **Health Check:** `/actuator/health`
- **Auto-deploy:** Enabled on push to main branch

> **Note:** Free tier services spin down after 15 minutes of inactivity. First request after sleep takes 30-60 seconds.
```

### Access Points
- **API Base URL**: `http://localhost:8080/api/applications`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:file:./data/jobtracker`
  - Username: `sa`
  - Password: _(leave empty)_

## 📡 API Endpoints

### Job Applications

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/applications` | Create new application |
| GET | `/api/applications` | Get all applications |
| GET | `/api/applications/{id}` | Get application by ID |
| PUT | `/api/applications/{id}` | Update application |
| DELETE | `/api/applications/{id}` | Delete application |
| GET | `/api/applications/status/{status}` | Filter by status |
| GET | `/api/applications/active` | Get active applications |
| GET | `/api/applications/interview` | Get applications in interview |
| GET | `/api/applications/stats` | Get statistics |

### Example Request

**Create Application:**
```bash
curl -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "TechCorp",
    "jobTitle": "Senior Software Engineer",
    "status": "APPLIED",
    "location": "Remote",
    "salaryMin": 150000,
    "salaryMax": 180000,
    "jobUrl": "https://example.com/job",
    "notes": "Submitted through referral"
  }'
```

### Application Statuses

- `RESEARCHING` - Initial research phase
- `APPLIED` - Application submitted
- `PHONE_SCREEN` - Phone screening scheduled/completed
- `TECHNICAL_INTERVIEW` - Technical interview phase
- `ONSITE_INTERVIEW` - On-site/final interviews
- `OFFER` - Offer received
- `REJECTED` - Application rejected
- `WITHDRAWN` - Application withdrawn
- `ACCEPTED` - Offer accepted

## 🧪 Testing

Run all tests:
```bash
mvn test
```

### Test Coverage
- **Service Layer**: 11 unit tests covering all business logic
- **Repository Layer**: Integration with Spring Data JPA
- **Mocking**: Comprehensive use of Mockito for unit testing

## 🏗️ Project Structure
```
src/
├── main/
│   ├── java/com/jobtracker/
│   │   ├── controller/          # REST endpoints
│   │   ├── service/              # Business logic
│   │   ├── repository/           # Data access
│   │   ├── entity/               # JPA entities
│   │   ├── dto/                  # Data transfer objects
│   │   └── exception/            # Exception handling
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/jobtracker/
        ├── service/              # Service layer tests
        └── controller/           # Controller tests (if applicable)
```

## 🎨 Design Decisions

### Why This Architecture?

**Repository Pattern**: Abstracts data access, making it easy to switch databases or add caching without changing business logic.

**Service Layer**: Centralizes business logic and transaction management, keeping controllers thin and focused on HTTP concerns.

**DTO Pattern**: Separates API contracts from domain models, allowing independent evolution and preventing over-exposure of internal structure.

**Java Records for DTOs**: Immutable, concise data carriers with built-in equals/hashCode/toString - perfect for DTOs.

### Database Choice

H2 was chosen for simplicity and portability. For production, this would be replaced with PostgreSQL or MySQL by simply changing the `application.properties` configuration.

## 🔄 CI/CD Pipeline

GitHub Actions automatically:
1. Builds the project on every push to main
2. Runs all unit tests
3. Packages the application as a JAR
4. Uploads test results and artifacts

See `.github/workflows/maven.yml` for configuration.

## 📈 Future Enhancements

- [ ] Add PostgreSQL support for production
- [ ] Implement frontend UI (React/Vue)
- [ ] Add authentication/authorization (Spring Security)
- [ ] Docker containerization
- [ ] Add document upload capability
- [ ] Email notifications for status changes
- [ ] Interview scheduling integration
- [ ] Resume/cover letter management

## 👨‍💻 Development

### Code Style
- Follow standard Java conventions
- Use meaningful variable/method names
- Keep methods focused and small
- Write tests for new features

### Adding New Features
1. Create feature branch
2. Implement with tests
3. Ensure all tests pass
4. Submit pull request

## 📝 License

This project is open source and available under the MIT License.

## 🤝 Contact

**Your Name**
- GitHub: [@p3bustos](https://github.com/p3bustos)

---

Built with ☕ and Spring Boot