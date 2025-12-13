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
![Login page](login.png)

![Dashboard](dashboard.png)

![Building manage](building_manage.png)
![Building filter](building_filter.png)
![Building add](building_add.png)
![Building update](building_update.png)
![Building detail](building_detail.png)

![Contract manage](contract_manage.png)
![Contract add](contract_add.png)
![Contract update](contract_update.png)
![Contract detail](contract_detail.png)


![Customer manage](customer_manage.png)
![Customer add](customer_add.png)
![Customer detail](customer_detail.png)

![Staff manage 1](staff_manage_1.png)
![Staff manage 2](staff_manage_2.png)
![Staff add](staff_add.png)
![Staff detail](staff_detail.png)

![Customer home](customer_home.png)
![Customer building view](customer_buildingView.png)
![Customer contract](customer_contract.png)
![Customer payment](customer_payment.png)
![Customer transaction history](customer_transaction_history.png)

![Public page](public_page.png)

## How to Run
1. Clone repository
2. Configure MySQL in `application.properties`
3. Run application using Spring Boot
