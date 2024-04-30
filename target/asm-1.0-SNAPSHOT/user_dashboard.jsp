<%-- 
    Document   : user_dashboard
    Created on : 2024年4月28日, 下午2:54:00
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>User Dashboard</title>
    </head>
    <body>
        <h1>Welcome, User!</h1>
        <nav>
            <ul>
                <li><a href="/UserDashboard">DashBoard</a></li>
                <li><a href="/EquipmentController">All Equipment</a></li>
                <li><a href="borrowing_records.jsp">My Reservations</a></li>
                <li><a href="profile.jsp">Profile</a></li>
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
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <footer>
                <p><a href="main?action=logout">Logout</a></p>
            </footer>
    </body>
</html>