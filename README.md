# HKITI Centralized Equipment Management System

## Project Overview

This document details the user roles and functionalities within the HKITI system, aimed at facilitating equipment borrowing, management, and delivery for General Users, Technicians, Couriers, and Staff, with tailored permissions and functionalities for each role.

## Prerequisites

- **Java JDK 11 or higher**
- **Apache NetBeans IDE**: Recommended version 12.0 or higher
- **XAMPP 8.0.30 or higher**: Includes MySQL Server and PHPMyAdmin
- **GlassFish Server 5.1.0 or higher**

## Skills Requirements

Our application leverages Java EE technologies to create a robust architecture:

- **JSP/Servlets**: Dynamically generate HTML pages and manage server-side logic.
- **User Input Handling**: Handle inputs from the browser using JSP/Servlets for dynamic interaction.
- **Custom Tag (Taglib)**: Enhance readability and reusability of JSPs through custom tags.
- **JavaBeans**: Encapsulate business logic in reusable components.
- **JDBC**: Manage database connectivity.
- **Session Checking**: Enhance security and user experience by maintaining user sessions.
- **Login Control**: Implement secure authentication mechanisms.
- **MVC Model**: Employ Model-View-Controller architecture for maintainability.
- **Additional Technologies**: Utilize AJAX, CSS3, and JavaScript for responsive design.

## Functionalities and Web Design

Designed to meet high standards of user experience and technical performance:

- **Complete User Requirements**: Implementation of all specified features.
- **Consistency and Usability**: Intuitive design across all interfaces.
- **Smooth Navigation**: Thoughtful navigation scheme for effortless feature access.
- **Tidy Page Layout**: Logical layout with related graphics for aesthetic appeal.
- **Error-Free Implementation**: Rigorous testing to ensure smooth application operation.
- **Creativity**: Innovative solutions for unique project challenges.

-  **Extra Function**: Hash Password, 

## User Roles and Functionalities

### User

- **View Available Devices**: Browse and view all available devices for borrowing.
- **Manage Personal Borrowing Records**: Check past and current equipment borrowings.
- **Add/Remove from Wish List**: Manage wish list items.
- **Reserve Equipment**: Reserve equipment for checkout.
- **Return Equipment**: Manage equipment returns.
- **Update Personal Information**: Securely update login credentials and personal details.

### Technician

- **Inventory Records Management**: Maintain up-to-date inventory records.
- **Handle Booking Records**: Manage and approve reservations, arrange deliveries.
- **Report and Manage Damages**: Handle damage reports and management.

### Courier

- **Pickup Equipment**: Manage equipment pickups.
- **Deliver Equipment**: Ensure timely and safe equipment deliveries.
- **Manage Delivery Status**: Track and update delivery statuses.

### Technician (Administrator)

- **Inherits All User and Technician Functionalities**
- **Equipment Checkout Statistics**: Analyze equipment usage trends.
- **Review Damage Reports**: Confirm damage reports.
- **Account Management**: Manage user accounts for all roles.

## Database Design

Our application utilizes a relational database structured into several key tables. Below is an overview of each table along with their primary purpose and relationships:

### Users

Stores user account information.

- **UserID** (PK): Unique identifier for each user.
- **Username**: Unique username for user login.
- **Password**: Encrypted password for user login.
- **Role**: Role of the user (e.g., GeneralUser, Technician).
- **FullName**: Full name of the user.
- **CampusName**: Name of the campus the user is associated with.

### Equipment

Stores details about equipment.

- **EquipmentID** (PK): Unique identifier for each piece of equipment.
- **Name**: Name of the equipment.
- **Description**: Description of the equipment.
- **Available**: Availability status of the equipment.
- **CampusName**: Original campus of the equipment.
- **CurrentCampus**: Current campus where the equipment is located.
- **EquipmentCondition**: Condition of the equipment.
- **ExclusiveForStaff**: Whether the equipment is exclusively for staff use.

### Reservation

Manages reservations of equipment.

- **ReservationID** (PK): Unique identifier for each reservation.
- **UserID**: References the user making the reservation.
- **ReservedFrom**: Start date of the reservation.
- **ReservedTo**: End date of the reservation.
- **DestinationCampus**: Campus where the equipment is to be delivered.

### ReservationEquipment

Links reservations to specific equipment items.

- **ReservationID, EquipmentID** (PK): Composite primary key linking to reservations and equipment.

### BorrowingRecords

Tracks the borrowing history of equipment.

- **RecordID** (PK): Unique identifier for each borrowing record.
- **ReservationID**: References the corresponding reservation.

### Wishlist

Maintains a list of equipment users wish to borrow.

- **WishlistID** (PK): Unique identifier for each wishlist entry.
- **UserID, EquipmentID**: Composite unique constraint ensuring no duplicate entries for the same user and equipment.

### Delivery

Manages the logistics of equipment delivery.

- **DeliveryID** (PK): Unique identifier for each delivery.
- **ReservationID**: Reservation linked to the delivery.
- **FromCampus, ToCampus**: Start and destination campuses for the delivery.
- **CourierID**: References the courier handling the delivery.

### DamageReports

Records reports of equipment damage.

- **ReportID** (PK): Unique identifier for each damage report.
- **EquipmentID**: Equipment being reported.
- **ReportedBy**: User who reported the damage.

### Campus

Details about each campus.

- **CampusName** (PK): Unique identifier for each campus.
- **Address**: Physical address of the campus.

### Notifications

Manages system notifications for users.

- **NotificationID** (PK): Unique identifier for each notification.
- **UserID**: User to whom the notification is sent.
- **Message**: Content of the notification.
- **ReadStatus**: Read status of the notification.

## Site Map

### General Structure

- **Login**

  - **User, Staff, Technician, Administrator, Courier Dashboards**
  - **Profile Management**
  - **Notification Systems**
  - **Inventory and Bookings Management**
  - **Delivery and Damage Report Handling**
  - **Account and Report Management**

  ### Login Page

- **Initial Entry Point for All Users**
- **Authentication Mechanism**: Users log in through this page to access their respective dashboards based on their role.

### Common Features Across All Roles

- **Dashboard**: Central hub where users can quickly access major features relevant to their role.
- **Notification System**: Alerts and messages related to equipment, reservations, and system updates.
- **Profile Management**: Allows users to update their personal information and change passwords.

### User Specific Features

- **User Dashboard**: Overview of available devices, current reservations, and borrowing records.
- **Equipment View**: Users can browse, search, and filter available equipment to reserve or add to their wishlist.
- **Borrowing Record**: History of past and current equipment borrowings.
- **Wish List**: Manage and review equipment they are interested in borrowing in the future.

### Staff Specific Features

- Includes all User features.
- **Equipment View (Exclusive For Staff)**: Access to additional equipment reserved exclusively for staff use.

### Technician Specific Features

- **Technician Dashboard**: Management tools for inventory and reservations.
- **Inventory Management**:
  - **Inventory View & Edit**: Manage and update equipment details.
  - **Equipment Check-In & Damage Report**: Log equipment returns and report any damages.
- **Bookings Management**:
  - **Approve or Decline Requests**: Handle booking requests from users.
  - **View All Booking Requests**: Comprehensive view of all pending and approved reservations.
- **Delivery Arrangement**: Organize and manage the logistics for equipment delivery to and from campuses.

### Administrator Specific Features

- Inherits all features from the Technician.
- **Account Management**:
  - **Create Account**: Add new users to the system.
  - **Edit User Account**: Update user roles and information.
- **Report**:
  - **View Damage Report**: Overview of reported and confirmed equipment damages.
  - **Booking Rate Analytics**: Analyze usage statistics for booked equipment.
- **Additional Administrative Controls**.

### Courier Specific Features

- **Courier Dashboard**: Access to pickup and delivery tasks.
- **View & Pickup Equipment**: List of equipment ready for pickup.
- **Update Delivery Status**: Track and update the status of deliveries in real-time.

### Navigation Logic

- **Dynamic Navigation**: Depending on the user's role, the navigation menu dynamically adjusts to present the most relevant options and tools.
- **Breadcrumb Trails and Contextual Navigation**: Help users understand their location within the application and facilitate quick movements between sections.

##

- Login

  - User

    - Dashboard
    - Notification
    - Profile
    - Wish List
    - Borrowing Record
    - Equipment View

  - Staff

    - Dashboard
    - Notification
    - Profile
    - Wish List
    - Borrowing Record
    - Equipment View(Exclusive For Staff)

  - Technician

    - Dashboard
    - Notification
    - Profile
    - Inventory Management
      - Inventory View & Edit
      - Equipment CheckIn & Damage Report
    - Bookings Management
      - Approve or Decline Request
      - View All Booking Request
    - Delivery Arrange

  - Administrator
    - Dashboard
    - Notification
    - Profile
    - Inventory Management
      - Inventory View & Edit
      - Equipment CheckIn & Damage Report
    - Bookings Management
      - Approve or Decline Request
      - View All Booking Request
    - Account Management
      - Create Account
      - Edit User Account
    - Report
      - View Damage Report
      - Booking Rate Analytic
    - Delivery Arrange

  -Courier

  - View & Pickup Equipment
  - Update Delivery Status

# Jsp Page

    - login.jsp

        - User
            - user_dashboard.jsp
            - equipment_list.jsp
            - borrowing_records.jsp
            - profile.jsp
            - userNotifications.jsp
            - wishlist.jsp

        - Staff
            - staff_dashboard.jsp
            - equipment_list.jsp
            - borrowing_records.jsp
            - profile.jsp
            - userNotifications.jsp
            - wishlist.jsp

        - Technician
            - technician_dashboard.jsp
            - Inventory Management
                - inventory_management.jsp
                - checkin.jsp
            - Bookings Management
                - booking_management.jsp
                - booking_details.jsp
            - arrange_delivery.jsp

        - Administrator
            - admin_dashboard.jsp
            - Inventory Management
                - inventory_management.jsp
                - checkin.jsp
            - Bookings Management
                - booking_management.jsp
                - booking_details.jsp
            - arrange_delivery.jsp
            - Report
                -damage_report_management.jsp
                -check_out_stats.jsp
            - manageUsers.jsp

        -Courier
            - delivery_management.jsp
            - updateDeliveryStatus.jsp

# Create Database

RunAllTests.java
-createdb.java
-createTable.java
-TestAddCampus.java
-TestAddEquipment.java
-TestAddUser.java

# DataBase Java

    AsmDB.java

### Java Beans

**Model Components**:

    - BorrowingRecordsBean.java
    - CampusStats.java
    - DamageReportsBean.java
    - DeliveryBean.java
    - EquipmentBean.java
    - EquipmentStats.java
    - ItemBean.java
    - NotificationBean.java
    - ReservationBean.java
    - UserBean.java
    - WishlistBean.java

### Tags

- **Custom JSP Tags**: ErrorMessageTag.java.

### Servlets

- **Controllers**:
  - AppContextListener.java(auto-generated by the system about overdue notices)
  - AdminController.java
  - ArrangeDeliveryController.java
  - BookingController.java
  - BookingDetailController.java
  - BorrowingController.java
  - CheckInController.java
  - CheckOutStatsController.java
  - DamageController.java
  - DeliveryController.java
  - EquipmentController.java
  - InventoryController.java
  - LoginController.java
  - ManageUsersController.java
  - NotificationController.java
  - ProfileController.java
  - StaffController.java
  - TechController.java
  - UpdateDeliveryStatusController.java
  - UserDashboardServlet.java
  - WishlistController.java

