<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Borrowing Records</title>
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
                padding-right: 20px;
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
                <a class="navbar-brand" href="#">Borrowing Records</a>
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
            <h2 class="my-4 text-center">My Borrowing Records</h2>

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Record ID</th>
                        <th>Equipment Name</th>
                        <th>Quantity</th>
                        <th>Borrow Date</th>
                        <th>Return Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="record" items="${borrowingRecords}">
                        <tr>
                            <td>${record.recordID}</td>
                            <td>${record.equipmentNames}</td>
                            <td>${record.totalQuantity}</td>
                            <td><fmt:formatDate value="${record.borrowDate}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${record.returnDate != null}">
                                        <fmt:formatDate value="${record.returnDate}" pattern="yyyy-MM-dd"/>
                                    </c:when>
                                    <c:otherwise>
                                        Not Returned
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${record.status}</td>
                            <td>
                                <c:if test="${record.status eq 'success'}">
                                    <form action="/BorrowingController" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="returnEquipment"/>
                                        <input type="hidden" name="recordID" value="${record.recordID}"/>
                                        <button type="submit" class="btn btn-primary">Return Equipment</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- Bootstrap JS Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>
    </body>
</html>
