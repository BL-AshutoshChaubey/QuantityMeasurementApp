# 📐 Quantity Measurement Application

A full-stack, microservices-based application for physical quantity conversions and arithmetic operations, built with **Spring Boot**, **Spring Cloud**, and **React**.

---

## 🏗️ Architecture

```
┌──────────────┐     ┌──────────────────┐     ┌────────────────────┐
│   React UI   │────▶│   API Gateway    │────▶│  Eureka Discovery  │
│  (Vite/Nginx)│     │   (Port 8080)    │     │   (Port 8761)      │
└──────────────┘     └──────┬───────────┘     └────────────────────┘
                            │
              ┌─────────────┼─────────────┐
              ▼                           ▼
   ┌──────────────────┐        ┌──────────────────┐
   │ Identity Service │        │Measurement Service│
   │   (Port 8082)    │        │   (Port 8081)     │
   │  - Registration  │        │  - Conversion     │
   │  - Login (JWT)   │        │  - Arithmetic     │
   │  - Google OAuth2 │        │  - History (Auth) │
   └────────┬─────────┘        └────────┬──────────┘
            │                           │
            └───────────┬───────────────┘
                        ▼
                 ┌──────────────┐
                 │    MySQL     │
                 │  Database    │
                 └──────────────┘
```

## ✨ Features

### Core Functionality
- **Unit Conversion**: Convert between Length (Feet, Inches, Yard, Mile), Weight (Gram, Kilogram, Tonne), Volume (Litre, Millilitre, Gallon), and Temperature (Celsius, Fahrenheit)
- **Arithmetic Operations**: Add, Subtract, and Divide quantities across compatible units
- **Audit History**: Logged-in users get a persistent history trail of all operations

### Security & Auth
- **JWT Authentication**: Stateless, token-based security across microservices
- **Google OAuth2**: One-click sign-in via Google
- **Public + Protected Access**: Dashboard is accessible to guests; history requires login

### Architecture
- **Spring Cloud Microservices**: Eureka Service Discovery, API Gateway routing
- **Decoupled Services**: Identity and Measurement services operate independently
- **Cloud-Ready**: Dockerized with CI/CD pipeline (Jenkins)

---

## 🛠️ Tech Stack

| Layer          | Technology                                      |
| :------------- | :---------------------------------------------- |
| **Frontend**   | React 18, Vite, Material UI, Axios              |
| **Backend**    | Spring Boot 3.2, Spring Cloud 2023.0            |
| **Gateway**    | Spring Cloud Gateway (Reactive)                 |
| **Discovery**  | Netflix Eureka Server                           |
| **Security**   | Spring Security, JWT (jjwt), Google OAuth2      |
| **Database**   | MySQL 8.0, Spring Data JPA, Hibernate           |
| **DevOps**     | Docker, Docker Compose, Jenkins, Nginx          |
| **Testing**    | JUnit 5, Mockito                                |

---

## 📂 Project Structure

```
quantitymeasurementApp/
├── infrastructure/
│   ├── eureka-server/          # Service Registry (Port 8761)
│   └── api-gateway/            # API Gateway & CORS (Port 8080)
├── services/
│   ├── identity-service/       # Auth, JWT, OAuth2 (Port 8082)
│   └── measurement-service/    # Core Logic & History (Port 8081)
├── quantity-measurement-ui/    # React Frontend (Port 5173)
├── docker-compose.yml          # Container Orchestration
├── Jenkinsfile                 # CI/CD Pipeline
└── pom.xml                     # Parent Maven POM
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8.0
- Maven 3.8+ (or use the included `mvnw` wrapper)

### 1. Clone the Repository
```bash
git clone https://github.com/BL-AshutoshChaubey/QuantityMeasurementApp.git
cd QuantityMeasurementApp
```

### 2. Configure Environment
Create a `.env` file in the project root:
```properties
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
JWT_SECRET_KEY=your-jwt-secret-key
```

### 3. Start Backend Services
Run each command in a **separate terminal** from the project root:

```bash
# 1. Eureka Server (start first, wait for it to be ready)
./mvnw spring-boot:run -pl infrastructure/eureka-server

# 2. Identity Service
./mvnw spring-boot:run -pl services/identity-service

# 3. Measurement Service
./mvnw spring-boot:run -pl services/measurement-service

# 4. API Gateway
./mvnw spring-boot:run -pl infrastructure/api-gateway
```

### 4. Start Frontend
```bash
cd quantity-measurement-ui
npm install
npm run dev
```

The app will open at **http://localhost:5173**

### 5. Docker (Alternative)
```bash
docker-compose up --build
```

---

## 🔌 API Endpoints

### Auth (via Gateway → Identity Service)
| Method | Endpoint                  | Description           | Auth     |
| :----- | :------------------------ | :-------------------- | :------- |
| POST   | `/api/v1/auth/register`   | Register new user     | Public   |
| POST   | `/api/v1/auth/login`      | Login & get JWT token | Public   |

### Measurements (via Gateway → Measurement Service)
| Method | Endpoint                        | Description              | Auth       |
| :----- | :------------------------------ | :----------------------- | :--------- |
| POST   | `/api/v1/measurements/convert`  | Convert between units    | Public     |
| POST   | `/api/v1/measurements/arithmetic` | Perform arithmetic     | Public     |
| GET    | `/api/v1/measurements/history`  | Get user's history       | JWT Required |

---

## 🌿 Branch Strategy

Each use case is developed on a dedicated branch:

| Branch | Description |
| :----- | :---------- |
| `uc1` - `uc9` | Core domain logic (Quantity, Units, Conversion, Arithmetic) |
| `uc10` - `uc14` | Generic refactoring, Volume, Temperature support |
| `uc15` - `uc16` | N-Tier Architecture, Database Integration |
| `uc17` | Spring Boot REST API |
| `uc18` | Security with OAuth2 & JWT |
| `uc19` - `uc20` | Frontend (Vanilla → React) |
| `uc21` | Microservices Architecture (Spring Cloud) |
| `uc22` | CI/CD & Cloud Deployment (Docker, Jenkins) |

---

## 📄 License

This project is developed as part of the **BridgeLabz  Program**.

---

## 👤 Author

**Ashutosh Chaubey**  
[![GitHub](https://img.shields.io/badge/GitHub-BL--AshutoshChaubey-blue?logo=github)](https://github.com/BL-AshutoshChaubey)
