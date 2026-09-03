# MyLocal Banking Buddy (MLBB)

A simple digital banking and e-wallet application created to practice Java programming, database connectivity, and backend web services.

---

## 📢 Disclaimer
**MLBB** stands for **My Local Banking Buddy**. This is an educational learning project and is **not affiliated** with the game *Mobile Legends: Bang Bang* or any actual commercial banking institution.

---

## 🚀 Project Description
The **MyLocal Banking Buddy (MLBB)** app simulates a basic e-wallet environment. Designed with ease of use in mind, the application supports fundamental banking features such as cashing in, cashing out, sending money, and securely viewing transaction history. This project serves as a robust foundational starting point for future digital banking enhancements.

---

## 🛠️ Tools & Technologies Used

### Frontend & UI
* **Java Swing (GUI):** Used for building the application's native desktop graphical interface.
* **FlatLaf:** A modern Look and Feel library used to give the Java Swing GUI a clean, contemporary theme.

### Backend & Core Frameworks
* **Spring Boot:** Configured via Spring Initializr to bootstrap the project framework and manage backend dependencies effectively.
* **JDBC (Java Database Connectivity):** Used to bridge and manage the connection between the Java application logic and the database.

### Database & Environment
* **IntelliJ IDEA Community Edition:** The primary Integrated Development Environment (IDE) used for writing and managing the source code.
* **XAMPP:** Used to host and run the local Apache server and MySQL database instance.
* **MySQL:** The relational database management system used for securely storing user credentials and transaction records.
* **phpMyAdmin:** Web-based GUI database tool used to easily view, inspect, and manage the underlying database tables.

---

## 🏛️ System Overview

The MLBB ecosystem is divided into several modular components that work seamlessly together:

* **Main Dashboard:** The entry point of the application where users are presented with options to either Log In or Sign Up.
* **Registration Form:** Allows new users to easily create a secure account by inputting their Name, Mobile Number, and a secure MPIN.
* **Login Form:** Allows existing users to securely access their accounts using their registered Mobile Number and MPIN.
* **Account Dashboard:** The main user hub post-login. Users can check their current balance, view recent transactions, cash in, cash out, or update their MPIN security settings.
* **Admin Dashboard:** A specialized administrative management panel used to monitor all registered users and global transaction histories within the system.
