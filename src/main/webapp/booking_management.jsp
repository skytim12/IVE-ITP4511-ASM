
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Booking Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script>
            function toggleDetails(rowId) {
                var detailsRow = document.getElementById('details' + rowId);
                if (detailsRow.style.display === 'none') {
                    detailsRow.style.display = 'table-row';
                } else {
                    detailsRow.style.display = 'none';
                }
            }

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

            .dropdown-item{
                font-size: 20px !important;
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

                <c:if test="${not empty dashboardURL}">
                    <a class="navbar-brand" href="${dashboardURL}">Reservation Review</a>
                </c:if>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTech" aria-controls="navbarTech" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarTech">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="inventoryDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Manage Inventory
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="bookingDropdown">
                                <li><a class="dropdown-item" href="/InventoryController">Show Inventory</a></li>
                                <li><a class="dropdown-item" href="/CheckInController">Check In</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="bookingDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Manage Bookings
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="bookingDropdown">
                                <li><a class="dropdown-item" href="/BookingController">Reservation Review</a></li>
                                <li><a class="dropdown-item" href="/BookingDetailController">All Booking Records</a></li>
                            </ul>
                        </li>

                        <li class="nav-item"><a class="nav-link" href="/ArrangeDeliveryController">Arrange Delivery</a></li>
                        <li class="nav-item"><a class="nav-link" href="main?action=logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-4 container2">
            <h2 class="text-center my-4">Booking Management</h2>
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Reservation ID</th>
                        <th>User ID</th>
                        <th>User Name</th>
                        <th>Reserved From</th>
                        <th>Reserved To</th>
                        <th>Toggle</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reservation" items="${reservations}" varStatus="status">
                        <tr>
                            <td>${reservation.reservationID}</td>
                            <td>${reservation.userID}</td>
                            <td>${reservation.userName}</td>
                            <td>${reservation.reservedFrom}</td>
                            <td>${reservation.reservedTo}</td>
                            <td><button type="button" class="btn btn-info" onclick="toggleDetails(${status.index});">Toggle Details</button></td>
                        </tr>
                        <tr id="details${status.index}" style="display:none;">
                            <td colspan="7">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Equipment ID</th>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Campus Name</th>
                                            <th>Current Campus</th>
                                            <th>Condition</th>
                                            <th>Exclusive For Staff</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="equipment" items="${reservation.equipmentList}">
                                            <tr>
                                                <td>${equipment.equipmentID}</td>
                                                <td>${equipment.name}</td>
                                                <td>${equipment.description}</td>
                                                <td>${equipment.campusName}</td>
                                                <td>${equipment.currentCampus}</td>
                                                <td>${equipment.condition}</td>
                                                <td>${equipment.exclusiveForStaff}</td>
                                                <td>${equipment.status}</td>
                                                <td>
                                                    <form action="BookingController" method="post">
                                                        <input type="hidden" name="reservationID" value="${reservation.reservationID}" />
                                                        <input type="hidden" name="equipmentID" value="${equipment.equipmentID}" />
                                                        <button type="submit" name="action" value="Accept" class="btn btn-success btn-sm">Accept</button>
                                                        <button type="submit" name="action" value="Decline" class="btn btn-danger btn-sm">Decline</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>
    </body>
</html>
