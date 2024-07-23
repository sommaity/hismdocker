1.	Project Overview: 
The "Healthcare Inventory Management System" project aims to develop an efficient solution for managing medical supplies and equipment within healthcare facilities. This involves creating a backend using Spring Boot for inventory management and a user-friendly frontend using React.
2.	Project Scope:
It focuses on building a system that enables healthcare providers to manage inventory levels, track usage, and order supplies. The backend will consist of Spring APIs, while React will be used for the frontend. The solution will optimize inventory management and enhance patient care.
3.	Business Problem:
Inaccurate inventory management leads to stockouts and increased operational costs in healthcare facilities. This project addresses the need for a streamlined platform that simplifies inventory tracking, supply orders, and stock level monitoring.
4.	Project Requirements:
  4.1.	Functional Requirements:
  User Story 1: User Registration and Authentication
    •	As a user, I want to register and log in to access the inventory management system. 
    Acceptance Criteria:
    •	Users should be able to create accounts with required information.
    •	Users should be able to securely log in and log out.
  User Story 2: Inventory Tracking
    •	As a healthcare provider, I want to track inventory levels and usage. 
    Acceptance Criteria:
      •	Providers should be able to view current inventory levels.
      •	Providers should be able to track usage history and trends.
  User Story 3: Supply Ordering
    •	As a provider, I want to place orders for medical supplies. 
    Acceptance Criteria:
      •	Providers should be able to create supply orders based on current stock levels.
      •	Providers should receive order confirmation and delivery status notifications.
  User Story 4: Stock Level Alerts
    •	As an administrator, I want to receive alerts for low stock levels. 
    Acceptance Criteria:
      •	Administrators should receive notifications when inventory levels fall below a certain threshold.
      •	Notifications should include details about the low-stock items.
  4.2.	Non-functional Requirements:
    •	Security: Implement secure authentication and data protection for user information and inventory data.
    •	User Experience: Design an intuitive, responsive, and user-friendly frontend interface.
5.	Data Model / Entity Description:
  5.1.	User Entity: 
  Attributes:
    •	UserID (Primary Key)
    •	FirstName
    •	LastName
    •	Email
    •	Password (Hashed)
  5.2.	Inventory Item Entity: 
  Attributes:
    •	ItemID (Primary Key)
    •	ItemName
    •	Description
    •	QuantityAvailable
    •	ReorderThreshold
    •	UnitPrice
  5.3.	Order Entity: 
  Attributes:
    •	OrderID (Primary Key)
    •	ProviderID (Foreign Key)
    •	OrderDate
    •	Status (Pending, Delivered, Cancelled)
6.	Architecture Design Guidelines:
  •	Communication: Implement RESTful APIs for communication between the frontend and backend.
  •	Database: Utilize MongoDB for backend data storage.
  •	Deployment: Host the frontend on a web server and deploy backend APIs using Spring Boot.

Project Deployment(Azure): https://sumansom.site

Spring Boot(Backend) Repository: https://github.com/sommaity/Healthcare-Inventory-Management-System

React.js(Frontend) Repository: https://github.com/sommaity/HealthCare-ReactJS
