<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Delivery Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
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

            .container2{
                min-height: 70vh;
            }

            .dropdown-item{
                font-size: 20px !important;
            }

            .table {
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .btn1{
                margin-bottom: 20px;
                padding: 10px 10px;
            }

        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container nav-container">
                <a class="navbar-brand" href="#">Delivery Schedule</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="/DeliveryController">Delivery Schedule</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/UpdateDeliveryStatusController">Delivery List</a>
                        </li>
                        <li class="nav-item"><a class="nav-link" href="main?action=logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container container2 mt-4">
            <h2 class="text-center my-4">Delivery Management</h2>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Delivery ID</th>
                        <th>Reservation ID</th>
                        <th>From Campus</th>
                        <th>To Campus</th>
                        <th>Status</th>
                        <th>Actions</th>
                        <th>Toggle Details</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="delivery" items="${deliveries}" varStatus="status">
                        <tr>
                            <td>${delivery.deliveryID}</td>
                            <td>${delivery.reservationID}</td>
                            <td>${delivery.fromCampus}</td>
                            <td>${delivery.toCampus}</td>
                            <td>${delivery.status}</td>
                            <td>
                                <form action="DeliveryController" method="post">
                                    <input type="hidden" name="deliveryID" value="${delivery.deliveryID}">
                                    <input type="hidden" name="action" value="updateStatus">
                                    <button type="submit" class="btn btn-primary">Update Status</button>
                                </form>
                            </td>
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
                                            <th>Destination Campus</th>
                                            <th>Condition</th>
                                            <th>Address</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${delivery.equipmentList}">
                                            <tr>
                                                <td>${item.equipmentID}</td>
                                                <td>${item.name}</td>
                                                <td>${item.description}</td>
                                                <td>${delivery.toCampus}</td>
                                                <td>${item.condition}</td>
                                                <td>${delivery.address}</td>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
