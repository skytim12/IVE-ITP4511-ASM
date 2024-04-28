<%-- 
    Document   : user_dashboard
    Created on : 2024年4月28日, 下午2:54:00
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>User Dashboard</title>
    </head>
    <body>
        <h1>Welcome, User!</h1>
        <nav>
            <ul>
                <li><a href="viewMyReservations.jsp">My Reservations</a></li>
                <li><a href="browseEquipment.jsp">Browse Equipment</a></li>
            </ul>
        </nav>
        <footer>
            <p><a href="main?action=logout">Logout</a></p>
        </footer>
    </body>
</html>