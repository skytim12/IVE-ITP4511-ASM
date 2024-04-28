<%-- 
    Document   : technician_dashboard
    Created on : 2024年4月28日, 下午2:53:27
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Technician Dashboard</title>
    </head>
    <body>
        <h1>Welcome, Technician!</h1>
        <nav>
            <ul>
                <li><a href="equipmentManagement.jsp">Manage Equipment</a></li>
                <li><a href="repairLogs.jsp">Repair Logs</a></li>
            </ul>
        </nav>
        <footer>
            <p><a href="main?action=logout">Logout</a></p>
        </footer>
    </body>
</html>