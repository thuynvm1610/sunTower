# Real Estate Management System

## Overview
A Spring Boot web application for managing buildings, customers, contracts,
invoices, and transactions with role-based access control.

## Tech stack
- Back-end: Spring Boot, Spring Security (JWT), JPA, MySQL.
- Front-end: Thymeleaf, Bootstrap, Ajax, JavaScript.

## Features
- RBAC: Admin (full CRUD), Staff (assigned scope), Customer (view contracts & invoices).
- Authentication & Authorization: JWT-based auth with BCrypt password hashing, role-based access control; OAuth2 login  for third-party authentication.
- Contract management: separate flows for rental (with monthly invoice generation) and purchase (one-time sale per building).
- Invoice & billing: monthly invoices with utility meter tracking per rental contract; integrated VNPay API for mock payment processing.
- Dynamic filtering & pagination: multi-criteria search using JPA Specification with server-side pagination.
- Digital map: coordinate picker using Google Maps JS SDK, location autocomplete & geocoding via Google Places API; Haversine-based radius search for nearby buildings.
- Real-time chat: implemented using WebSockets for instant messaging between users.

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
