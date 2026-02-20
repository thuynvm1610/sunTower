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
![Login page](screenshots/login.png)

![Dashboard](screenshots/dashboard.png)

![Building manage](screenshots/building_manage.png)
![Building filter](screenshots/building_filter.png)
![Building add](screenshots/building_add.png)
![Building update](screenshots/building_update.png)
![Building detail](screenshots/building_detail.png)

![Contract manage](screenshots/contract_manage.png)
![Contract add](screenshots/contract_add.png)
![Contract update](screenshots/contract_update.png)
![Contract detail](screenshots/contract_detail.png)


![Customer manage](screenshots/customer_manage.png)
![Customer add](screenshots/customer_add.png)
![Customer detail](screenshots/customer_detail.png)

![Staff manage 1](screenshots/staff_manage_1.png)
![Staff manage 2](screenshots/staff_manage_2.png)
![Staff add](screenshots/staff_add.png)
![Staff detail](screenshots/staff_detail.png)

![Customer home](screenshots/customer_home.png)
![Customer contract](screenshots/customer_contract.png)
![Customer payment](screenshots/customer_payment.png)
![Customer building view](screenshots/customer_buildingView.png)
![Customer transaction history](screenshots/customer_transaction_history.png)

![Public page](screenshots/public_page.png)

## How to Run
Read "guide.txt" file

## PROJECT IS FINISHED
