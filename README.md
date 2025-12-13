# Real Estate Management System

## Overview
A Spring Boot web application for managing buildings, customers, contracts,
invoices, and transactions with role-based access control.

## Technologies
- Java 17
- Spring Boot, Spring MVC, Spring Data JPA
- Spring Security, BCrypt
- Thymeleaf, Bootstrap, JQuery, Ajax
- MySQL

## System Roles
- Admin: Full system management (CRUD buildings, staffs, customers, contracts, invoices)
- Staff: Manage customers, contracts, invoices; track billing and transactions
- Customer: View contracts, invoices, transaction history; request profile updates
- Public: View building list without authentication

## Key Features
- Role-based authorization with Spring Security
- Secure password hashing using BCrypt
- Dynamic filtering and server-side pagination (JPA Specification + AJAX)
- RESTful APIs for asynchronous operations
- VNPay sandbox integration (mock payment)

## Database Design
![ERD](screenshots/estate_erd.png)

## Screenshots
(Add 3–5 UI screenshots)

## How to Run
1. Clone repository
2. Configure MySQL in `application.properties`
3. Run application using Spring Boot
