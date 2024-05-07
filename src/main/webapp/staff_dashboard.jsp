<%-- 
    Document   : staff_dashboard
    Created on : 2024年4月28日, 下午2:53:49
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Staff Dashboard</title>
        <
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>

            body {
                background-color: #222831;
            }

            .nav-container{
                background-color: #76ABAE !important;
            }

            .navbar-brand{
                font-size: 28px !important;
                font-weight: 500;
            }

            .navbar-nav .nav-link {
                font-size: 20px !important;
                font-weight: 500;
            }

            .nav-item{
                padding-right: 20px
            }

            .navbar {
                background-color: #76ABAE;
            }
            .container {
                margin-top: 50px;
                background-color: #EEEEEE;
            }

            .container h2{
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
                <a class="navbar-brand" href="/StaffDashboard">Staff Dashboard</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">

                        <li class="nav-item">
                            <a class="nav-link" href="/EquipmentController">Reservation</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/BorrowingController">My Booking Record</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/WishlistController">My WishList</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/NotificationController">Notification</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/ProfileController">Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="main?action=logout">Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <h2 class="mb-4 text-center">Reservable Equipment List</h2>

            <table class="table table-hover">
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
        </div>
       
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>
    </body>
</html>
