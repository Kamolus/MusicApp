# Spring Music App REST API

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)]()
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)]()
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)]()
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

## About the Project

**Spring Music App** is an advanced backend REST API environment for comprehensive management of the music industry. The application simulates real business processes occurring between musicians, bands, managers, and record labels.

The project was designed with high performance, security, and clean architecture in mind, utilizing the latest standards of the Spring ecosystem.

## Key Features

* **User and Role Management:** Advanced entity inheritance hierarchy (Musicians, Managers, Talent Scouts).
* **Security (Spring Security & JWT):** Authorization and authentication based on stateless JWT tokens. Protection against IDOR attacks using `@PreAuthorize` and per-token identification.
* **Business Negotiations and Contracts:** Complex state logic allowing for the creation, rejection, and acceptance of music contracts with automatic validation of active agreements.
* **Efficient Searching:** Implemented pagination and offset-based "Infinite Scroll" optimizing database queries when searching for musicians and bands.
* **Global Exception Handling:** Consistent and readable API responses using `@RestControllerAdvice` (e.g., for business logic exceptions, resource not found, or validation errors).
* **DTO Mapping:** Secure data transfer between application layers using the Data Transfer Object pattern.

## Technologies

* **Language:** Java
* **Framework:** Spring Boot 3 (Web, Data JPA, Security)
* **Database:** PostgreSQL
* **Security:** JSON Web Tokens (JWT)
* **Infrastructure:** Docker & Docker Compose
* **Tools:** Maven (Wrapper)

## How to run the project locally

### Prerequisites
* Docker and Docker Desktop / Docker Compose installed
* Java 17 or higher
