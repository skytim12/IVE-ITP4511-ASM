<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <title>My Wishlist</title>
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
                font-size: 32px !important;
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
            .container h2 {
                padding: 10px;
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
                <a class="navbar-brand" href="#">Wishlist</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <c:if test="${not empty dashboardURL}">
                            <li class="nav-item"><a class="nav-link" href="${dashboardURL}">Dashboard</a></li>
                            </c:if>
                        <li class="nav-item"><a class="nav-link" href="/EquipmentController">Reservation</a></li>
                        <li class="nav-item"><a class="nav-link" href="/BorrowingController">My Reservations</a></li>
                        <li class="nav-item"><a class="nav-link" href="/WishlistController">My Wishlist</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Notifications</a></li>
                        <li class="nav-item"><a class="nav-link" href="/ProfileController">Profile</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
             <h2 class="text-center my-4">My Wishlist</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Equipment ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${wishlist}">
                        <tr>
                            <td>${item.equipmentID}</td>
                            <td>${item.name}</td>
                            <td>${item.description}</td>
                            <td>
                                <form action="/WishlistController" method="post">
                                    <input type="hidden" name="action" value="removeFromWishlist"/>
                                    <input type="hidden" name="equipmentID" value="${item.equipmentID}"/>
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- Bootstrap JS Bundle -->
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
