<%-- 
    Document   : staff_dashboard
    Created on : 2024年4月28日, 下午2:53:49
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff Dashboard</title>
    </head>
    <body>
        <h1>Welcome, Staff!</h1>
        <nav>
            <ul>
                <li><a href="/StaffDashboard">DashBoard</a></li>
                <li><a href="/EquipmentController">Reservation</a></li>
                <li><a href="/BorrowingController">My Reservations Record</a></li>
                <li><a href="/WishlistController">My WishList</a></li>
                <li><a href="#">Notification</a></li>
                <li><a href="/ProfileController">Profile</a></li>
            </ul>
        </nav>

        <ul>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Available</th>
                        <th>Campus</th>
                        <th>Condition</th>
                        <th>Description</th> 
                        <th>Total</th>
                        <th>Current Campus</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="equipment" items="${equipmentList}">
                    <tr>
                        <td>${equipment.name}</td>
                        <td>${equipment.available}</td>
                        <td>${equipment.campusName}</td>
                        <td>${equipment.condition}</td>
                        <td>${equipment.description}</td>
                        <td>${equipment.totalQuantity}</td>
                        <td>${equipment.currentCampus}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <footer>
                <p><a href="main?action=logout">Logout</a></p>
            </footer>
    </body>
</html>