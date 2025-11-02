Setup and Installation Guide

This guide provides the necessary steps to set up the PawFect Smart CMS application, configure the
database, and run the Spring Boot service locally.

Prerequisites
• Java Development Kit (JDK) 21+
• MySQL Server (Running on localhost:3306)
• MySQL Workbench (Recommended for database management)
• Git installed.

1. Clone the Project
Open your terminal or command prompt and run the following command to download the code:
git clone https://github.com/SumitKuSinha/petStore_hack.git
cd petStore_hackathon

2. Database Setup
The application uses Spring Data JPA which requires an empty database to be set up first. Open
MySQL Workbench and execute the following command:
CREATE DATABASE petstore_db;

3. Application Configuration
    1. Open the project in your IDE (Eclipse/IntelliJ). 
    2. Navigate to src/main/resources/application.properties.
    3. Update the password field with your actual MySQL root password:spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD

4. Run the Application
Run the main class: PetstoreCmsApplication.java. The application will start on port 8088.
First-Time Admin Login Setup (Crucial Step)
Due to the robust Spring Security configuration, you must follow these steps exactly to create and
enable the Superuser account.

1. Create the User (ROLE USER)
• Go to the application in your browser: http://localhost:8088/.
• Click the Login link, then click Register.
• Create a user (e.g., Username: admin, Password: admin123).
• The user is created in the database with the default ROLE USER.

2. Promote the User (ROLE ADMIN)
• Open MySQL Workbench and go to the petstore db schema.
• Right-click on the users table and select Select Rows.
• Manually change the role column for the new user from ROLE USER to ROLE ADMIN.
• Click the Apply button to save the change to the database.

3. Access the Dashboard
• Go to http://localhost:8088/login.
• Log in with the credentials: Username: admin, Password: admin123.
• You will be automatically redirected to the secure Admin Dashboard.