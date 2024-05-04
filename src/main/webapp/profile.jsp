<%-- 
    Document   : profile
    Created on : 2024年4月8日, 上午6:33:38
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Profile</title>
    </head>
    <body>
        <h1>Your Profile</h1>
        <nav>
            <ul>
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="/EquipmentController">All Equipment</a></li>
                <li><a href="/BorrowingController">My Reservations Record</a></li>
                <li><a href="/WishlistController">My WishList</a></li>
                <li><a href="#">Notification</a></li>
                <li><a href="/ProfileController">Profile</a></li>
            </ul>
        </nav>

        <c:if test="${not empty userProfile}">
            <form action="/ProfileController" method="post">
                Full Name: <input type="text" name="name" value="${userProfile.fullName}"><br>
                New Password: <input type="password" name="newPassword"><br>
                Confirm Password: <input type="password" name="confirmPassword"><br>
                <input type="submit" value="Update Profile">
            </form>

        </c:if>

        <script>
            // Function to show a success message
            function showSuccessMessage(message) {
                alert(message);
            }

            // Function to show an error message
            function showErrorMessage(message) {
                alert(message);
            }

            // Check for success message from server and display
            <% if (request.getAttribute("successMessage") != null) { %>
            showSuccessMessage("<%= request.getAttribute("successMessage") %>");
            <% } %>

            // Check for error message from server and display
            <% if (request.getAttribute("errorMessage") != null) { %>
            showErrorMessage("<%= request.getAttribute("errorMessage") %>");
            <% } %>
        </script>

    </body>
</html>
