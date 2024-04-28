<%-- 
    Document   : admin_dashboard
    Created on : 2024年4月28日, 下午2:53:02
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashboard</title>
    </head>
    <body>
        <h1>Welcome, Admin!</h1>
        <nav>
            <ul>
                <li><a href="manageUsers.jsp">Manage Users</a></li>
                <li><a href="viewReports.jsp">View Reports</a></li>
                <li><a href="systemSettings.jsp">System Settings</a></li>
            </ul>
        </nav>
        <footer>
            <p><a href="main?action=logout">Logout</a></p>
        </footer>
    </body>
</html>
