# 📦 Stockify

**Stockify** is a desktop-based inventory management system developed in **Java**, featuring a graphical user interface and database persistence using **MySQL**.  
This project was created as the final assignment for the course **Techniques and Programming Practices (TPP – 2025B)** at **University of Vale do Taquari (UNIVATES)**.

---

## 📄 Project Overview

Stockify is designed to support the inventory and sales management of a small business.  
The system enables full control over **customers**, **product groups**, **products**, and **sales orders**, ensuring accurate stock updates and data consistency.

The application follows a layered architecture and applies industry-standard design patterns, focusing on maintainability, scalability, and clean code practices.

---

## 🚀 Features

### Customer Management
- Create, update, delete, and list customers

### Product Group Management
- Create, update, delete, and list product groups/classes

### Product Management
- Create, update, delete, and list products
- Filter products by group/class

### Sales Order Management
- Create new sales orders
- Edit or delete **non-finalized** orders
- Save orders without finalizing
- Finalize orders (lock editing and deletion)
- View open and finalized orders
- Search orders by customer and date range

### Inventory Control
- Automatic stock reduction upon order finalization
- Prevention of changes to finalized orders

---

## ✅ Requirements Compliance

### Functional Requirements
- Customer, product group, product, and order management
- Order lifecycle control (open, saved, finalized)
- Inventory synchronization with sales orders
- Advanced order search and filtering

### Non-Functional Requirements
- Developed in **Java**
- Desktop application with **Graphical User Interface (GUI)**
- Database persistence using **MySQL**
- Layered architecture:
  - Presentation
  - Business
  - Persistence
- Implementation of the **DAO (Data Access Object)** pattern
- Implementation of the **Factory** design pattern
- Full usage of the **Alexandria** library
- Proper exception handling using `try-catch` blocks

---

## 🏗️ System Architecture

The system is structured into clearly defined layers:

- **Presentation Layer**  
  Handles user interaction through graphical interfaces.

- **Business Layer**  
  Contains business rules and application logic.

- **Persistence Layer**  
  Responsible for database access, implemented using the DAO pattern and the Alexandria library.

---

## 🛠️ Technologies Used

- **Java**
- **Swing / Alexandria GUI Components**
- **MySQL Database**
- **Alexandria Framework**
- **Design Patterns**
  - DAO
  - Factory
---
