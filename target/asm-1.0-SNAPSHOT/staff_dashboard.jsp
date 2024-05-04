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
                <li><a href="/UserDashboard">DashBoard</a></li>
                <li><a href="/EquipmentController">Reservation</a></li>
                <li><a href="borrowing_records.jsp">My Reservations Record</a></li>
                <li><a href="/WishlistController">My WishList</a></li>
                <li><a href="#">Notification</a></li>
                <li><a href="profile.jsp">Profile</a></li>
            </ul>
        </nav>
        <footer>
            <p><a href="main?action=logout">Logout</a></p>
        </footer>
    </body>
</html>