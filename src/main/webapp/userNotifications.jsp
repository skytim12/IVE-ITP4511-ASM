    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Your Notifications</title>
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

            .container2{
                min-height: 70vh;
            }

            .container h2{
                padding: 10px;
            }
            .table {
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .btn1{
                margin: 20px 0px;
                padding: 10px 10px;
            }

        </style>
    </head>
    <body>

        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container nav-container">

                <c:if test="${not empty dashboardURL}">
                    <a class="navbar-brand" href="${dashboardURL}">Notification</a>
                </c:if>
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
        <div class="container mt-4 container2">

            <form action="NotificationController" method="post">
                <input type="hidden" name="action" value="markAllRead">
                <input type="hidden" name="userID" value="${userID}">
                <button type="submit" class="btn btn-primary btn1">Mark All as Read</button>
            </form>

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Message</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="notification" items="${notifications}">
                        <tr>
                            <td>${notification.message}</td>
                            <td><fmt:formatDate value="${notification.notificationDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>${notification.readStatus}</td>
                            <td> <c:if test="${notification.readStatus == 'Unread'}">
                                    <form action="NotificationController" method="post">
                                        <input type="hidden" name="action" value="markRead">
                                        <input type="hidden" name="notificationID" value="${notification.notificationID}">
                                        <button type="submit" class="btn btn-primary ">Read</button>
                                    </form>
                                </c:if></td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </body>
</html>
