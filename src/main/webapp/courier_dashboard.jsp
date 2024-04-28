<%-- 
    Document   : courier_dashboard
    Created on : 2024年4月28日, 下午2:53:41
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Courier Dashboard</title>
    </head>
    <body>
        <h1>Welcome, Courier!</h1>
        <nav>
            <ul>
                <li><a href="deliverySchedule.jsp">View Delivery Schedule</a></li>
                <li><a href="updateDeliveryStatus.jsp">Update Delivery Status</a></li>
            </ul>
        </nav>
        <footer>
            <p><a href="main?action=logout">Logout</a></p>
        </footer>
    </body>
</html>