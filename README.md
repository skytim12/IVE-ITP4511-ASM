# Project Overview

This document outlines the user roles and associated functionalities within the system. The project aims to facilitate equipment borrowing, management, and delivery across different user roles: Users (General Users), Technicians, Couriers, and Staff. Each role has specific permissions and functionalities tailored to their responsibilities.

## User Roles

### General Users (User)

- **View Available Equipment and Personal Borrowing Records**: Users can browse all borrowable equipment and view their past borrowing records.
- **Add/Remove from Wishlist**: Users can add temporarily unnecessary or unavailable equipment to a wishlist and remove them as needed.
- **Reserve, Borrow, and Return Equipment**: Users can reserve specific equipment for borrowing and return it within the stipulated time.
- **Update Password and Personal Information**: Users can update their login password and personal profile information.

### Technicians (Technician)

- **Maintain Inventory Records**: Technicians are responsible for updating and maintaining the inventory records of equipment.
- **Handle Reservation Records**: Review and manage user reservation requests, arrange for equipment delivery and collection, manage check-in and check-out of equipment.
- **Report Equipment Damage**: Record and report any damages found upon equipment return.

#### Additional Responsibilities for Administrator Technicians:

- **View Equipment and Campus Borrowing Statistics**: Analyze and report on the usage of different equipment and the equipment usage across campuses.
- **Review and Confirm Damage Reports**: Review damage reports submitted by technicians and take appropriate actions.
- **Create and Manage User Accounts**: Set up accounts for new users, assign appropriate roles and permissions.

### Couriers (Courier)

- **Receive Equipment Pickup Requests**: Couriers receive requests for equipment to be transported from one location to another.
- **Deliver Equipment to Designated Campuses**: Responsible for securely transporting equipment from storage to the user-specified campus or location.
- **Report Pickup and Delivery Status**: Update the system with the status of equipment pickups and deliveries, including times and locations.

### Staff

- **Access All General User Functions**: Staff can perform all operations available to general users, such as viewing equipment lists, reserving and borrowing equipment, viewing personal borrowing records, updating passwords, and personal information.
- **Access and Borrow Exclusive Equipment**: Some equipment may be exclusively available for staff, not accessible or reservable by general users. This distinction may be based on the equipment's sensitivity, value, or specific professional needs.
- **Potential Management Functions**: Depending on the system design and organizational needs, staff may also have mild managerial duties, such as managing certain types of equipment information, participating in decision-making processes, or providing feedback.

## JSP Pages

1. **Login Page (`login.jsp`)**: For user login.
2. **Equipment List Page (`equipment_list.jsp`)**: Displays all reservable equipment.
3. **Booking Details Page (`booking_details.jsp`)**: For viewing equipment details and making reservations.
4. **Borrowing Records Page (`borrowing_records.jsp`)**: Displays user's borrowing records and current borrowing status.
5. **Booking Management Page (`booking_management.jsp`)**: For technicians to review and manage reservations.
6. **Inventory Management Page (`inventory_management.jsp`)**: To add, edit, or delete equipment information.
7. **Damage Report Management Page (`damage_report_management.jsp`)**: For recording and reviewing equipment damage reports.
8. **Delivery Management Page (`delivery_management.jsp`)**: Displays current pickup and delivery requests.
9. **Profile Page (`profile.jsp`)**: Allows all users to update personal information and password.
10. **Registration Page (`register.jsp`)**: Optional, for user self-registration.
11. **Admin Dashboard (`admin_dashboard.jsp`)**: This dashboard is exclusive to administrators. It provides quick access to manage users, view system reports, and adjust system settings. Administrators can oversee and control various aspects of the system from this central hub.
12. **Technician Dashboard (`technician_dashboard.jsp`)**: Designed for technicians, this dashboard offers features to manage equipment and log repair activities. Technicians can access tools to handle equipment maintenance, updates, and review repair logs, ensuring equipment is always in optimal condition.
13. **Courier Dashboard (`courier_dashboard.jsp`)**: Couriers use this dashboard to view and manage their delivery schedules and update the status of deliveries. This page helps streamline the process of equipment delivery, ensuring efficient and timely operations.
14. **Staff Dashboard (`staff_dashboard.jsp`)**: This dashboard is tailored for staff members, providing functionalities to manage reservations and check equipment availability. It's designed to facilitate the daily operations of staff who handle booking and reservations of equipment.
15. **User Dashboard (`user_dashboard.jsp`)**: The user dashboard allows regular users to view their reservations and browse available equipment. This interface is user-friendly, providing essential functionalities to reserve equipment and review their borrowing status.

This README serves as a guideline for developing and navigating the system based on user roles and their respective functionalities.
