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
![0](screenshots/0.png)

## Screenshots
![1](screenshots/1.png)

![2](screenshots/2.png)

![3](screenshots/3.png)

![4](screenshots/4.png)

![5](screenshots/5.png)

![6](screenshots/6.png)

![7](screenshots/7.png)

![8](screenshots/8.png)

![9](screenshots/9.png)

![10](screenshots/10.png)

![11](screenshots/11.png)

![12](screenshots/12.png)

![13](screenshots/13.png)

## How to Run
Read "guide.txt" file

## PROJECT IS FINISHED
