<%-- 
    Document   : staff_dashboard
    Created on : 2024年4月28日, 下午2:53:49
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff Dashboard</title>
    </head>
    <body>
        <h1>Welcome, Staff!</h1>
        <nav>
            <ul>
                <li><a href="reservationManagement.jsp">Manage Reservations</a></li>
                <li><a href="checkEquipmentAvailability.jsp">Check Equipment Availability</a></li>
            </ul>
        </nav>
        <footer>
            <p><a href="main?action=logout">Logout</a></p>
        </footer>
    </body>
</html>