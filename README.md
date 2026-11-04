# Event Management System

A full-stack Event Management System built using React and Spring Boot.

The application allows users to register and log in, view available events, 
and book events. Administrators can create events and view users who have 
registered for each event.

## Features

### User

- User registration
- Login using username or email
- JWT-based authentication
- View available events
- Book an event
- Prevent duplicate booking for the same event
- User dashboard
- Role-based access

### Admin

- Admin login
- Create events
- View events
- View users registered for an event
- Role-based authorization

## Technologies Used

### Frontend

- React
- JavaScript
- Axios
- React Router
- HTML
- CSS
- Vite

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- Maven

### Database

- MySQL

## Project Structure

```text
Event-management-system/
│
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── App.jsx
│   │   ├── api.js
│   │   └── main.jsx
│   ├── package.json
│   └── vite.config.js
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   ├── pom.xml
│   └── ...
│
└── README.md