
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Booking Details</title>
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
                font-size: 18px !important;
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
                <a class="navbar-brand" href="#">Booking Details</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <c:if test="${not empty dashboardURL}">
                            <li class="nav-item"><a class="nav-link" href="${dashboardURL}">Dashboard</a></li>
                            </c:if>
                        <li class="nav-item"><a class="nav-link" href="inventory_management.jsp">Inventory Management</a></li>
                        <li class="nav-item"><a class="nav-link" href="booking_details.jsp">All Booking Record</a></li>
                        <li class="nav-item"><a class="nav-link" href="/ArrangeDeliveryController">Arrange Delivery</a></li>
                        <li class="nav-item"><a class="nav-link" href="damage_report_management.jsp">Damage Reports</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-4">
            <h2 class="mb-3">Equipment Booking Details</h2>
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
                            <td colspan="6">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Campus Name</th>
                                            <th>Current Campus</th>
                                            <th>Condition</th>
                                            <th>Exclusive For Staff</th>
                                            <th>Status</th>
                                           
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="equipment" items="${reservation.equipmentList}">
                                            <tr>
                                                <td>${equipment.name}</td>
                                                <td>${equipment.description}</td>
                                                <td>${equipment.campusName}</td>
                                                <td>${equipment.currentCampus}</td>
                                                <td>${equipment.condition}</td>
                                                <td>${equipment.exclusiveForStaff}</td>
                                                <td>${equipment.status}</td>
                                                
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

        <!-- Bootstrap JS Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>
    </body>
</html>
