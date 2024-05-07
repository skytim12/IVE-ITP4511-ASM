
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Profile</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #222831;
            }
            .nav-container {
                background-color: #76ABAE !important;
            }
            .navbar-brand, .navbar-nav .nav-link {
                font-size: 20px;
                font-weight: 500;
            }

            .navbar-brand{
                font-size: 28px !important;
                font-weight: 500;
            }

            .nav-item {
                padding-right: 40px;
            }
            .navbar {
                background-color: #76ABAE;
            }
            .container {
                margin-top: 50px;
                background-color: #EEEEEE;
            }

            .container2{
                height: 70vh;
            }
            h2 {
                padding: 20px;
            }
            .table {
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container nav-container">
                <c:if test="${not empty dashboardURL}">
                    <a class="navbar-brand" href="${dashboardURL}">My Profile</a>
                </c:if>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">

                        <li class="nav-item"><a class="nav-link" href="/EquipmentController">Reservation</a></li>
                        <li class="nav-item"><a class="nav-link" href="/BorrowingController">My Booking Record</a></li>
                        <li class="nav-item"><a class="nav-link" href="/WishlistController">My Wishlist</a></li>
                        <li class="nav-item"> <a class="nav-link" href="/NotificationController">Notification</a></li>
                        <li class="nav-item"><a class="nav-link" href="/ProfileController">Profile</a></li>
                        <li class="nav-item">
                            <a class="nav-link" href="main?action=logout">Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container mt-4">

            <h2 class="text-center mb-4">My Profile</h2>
            <div class="container2">
                <c:if test="${not empty userProfile}">
                    <form action="/ProfileController" method="post" class="w-50 mx-auto">
                        <div class="mb-3">
                            <label for="name" class="form-label">Full Name:</label>
                            <input type="text" class="form-control" id="name" name="name" value="${userProfile.fullName}">
                        </div>
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">New Password:</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword">
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirm Password:</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
                        </div>
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                    </form>
                </c:if>
            </div>


            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>
            <script>
                function showSuccessMessage(message) {
                    alert(message);
                }

                function showErrorMessage(message) {
                    alert(message);
                }

                <% if (request.getAttribute("successMessage") != null) { %>
                showSuccessMessage("<%= request.getAttribute("successMessage") %>");
                <% } %>

                <% if (request.getAttribute("errorMessage") != null) { %>
                showErrorMessage("<%= request.getAttribute("errorMessage") %>");
                <% } %>
            </script>
    </body>
</html>
