Event Pro - A Full-Stack Event Management System

Event Pro is a full-featured event management platform that simplifies the process of organizing and managing events. It enables users to register, manage events through a user-friendly dashboard, and receive real-time notifications. Designed with scalability, modularity, and performance in mind, Event Pro is ideal for both small meetups and large-scale event planning.

Features:

User Registration & Authentication
Secure sign-up and login system with role-based access controls.

Event Dashboard
A centralized, user-friendly dashboard for creating, editing, and deleting events.

Real-Time Notifications
Notify users about upcoming events, changes, and important updates.

Event Analytics
View metrics like number of attendees, engagement, and event success.

Modular Architecture
Built using design patterns for scalable and maintainable code.

Tech Stack:

Backend: Java (Spring Boot)
Frontend: HTML, CSS, JavaScript, Bootstrap
Database: MySQL
Version Control: Git

Project Structure:

EventPro/
├── backend/
│ ├── src/
│ │ ├── main/java/com/eventpro/
│ │ └── resources/
│ └── pom.xml
├── frontend/
│ ├── index.html
│ ├── css/
│ ├── js/
│ └── bootstrap/
├── README.txt
└── database/
└── schema.sql

Setup Instructions:

Clone the Repository:

git clone https://github.com/your-username/EventPro.git
cd EventPro

Backend (Spring Boot):

Import the project into your IDE (like IntelliJ or Eclipse)

Configure application.properties with your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/eventpro
spring.datasource.username=root
spring.datasource.password=your_password

Run the Spring Boot application using your IDE or with the command:
./mvnw spring-boot:run

Database Setup:

Create a database in MySQL named eventpro

Run the script found at database/schema.sql

Frontend:

Open index.html in any modern browser

Or use a local server like Live Server (VS Code extension)

Author:
Dinesh Chatla
Aspiring Software Engineer passionate about full-stack development and scalable architecture.

Email: dinesh.chatla.cse@gmail.com
LinkedIn: https://www.linkedin.com/in/dinesh-chatla-74501a301/
