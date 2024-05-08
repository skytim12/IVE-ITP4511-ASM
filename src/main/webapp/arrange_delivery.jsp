<%-- 
    Document   : arrange_delivery
    Created on : 2024年4月8日, 上午6:33:00
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arrange Delivery</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var arrangeDeliveryModal = document.getElementById('arrangeDeliveryModal');
                var modalInstance = new bootstrap.Modal(arrangeDeliveryModal, {
                    keyboard: false
                });

                window.arrangeDelivery = function (reservationID, toCampus) {
                    console.log('Opening modal for:', reservationID);
                    document.getElementById('modalReservationID').value = reservationID;
                    document.getElementById('toCampus').value = toCampus;


                    modalInstance.show();
                };

                arrangeDeliveryModal.addEventListener('shown.bs.modal', function () {
                    console.log('Modal is now visible');
                });

                arrangeDeliveryModal.addEventListener('hidden.bs.modal', function () {
                    console.log('Modal is now hidden');
                });

                document.querySelector('#arrangeDeliveryModal .btn-close').addEventListener('click', function () {
                    modalInstance.hide();
                });

                arrangeDeliveryModal.addEventListener('click', function (event) {
                    event.stopPropagation();
                });

                window.onclick = function (event) {
                    if (event.target === arrangeDeliveryModal) {
                        modalInstance.hide();
                    }
                };
            });

            window.toggleDetails = function (rowId) {
                var detailsRow = document.getElementById('details' + rowId);
                if (detailsRow.style.display === 'none') {
                    detailsRow.style.display = 'table-row';
                } else {
                    detailsRow.style.display = 'none';
                }
            };

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

            .container h2{
                padding: 10px;
            }
            .table {
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .container2{
                min-height: 70vh;
            }

            .dropdown-item{
                font-size: 20px !important;
            }

            .modal-backdrop {
                z-index: 1040;
            }

            .modal {
                z-index: 1050;
            }

        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container nav-container">
                <c:if test="${not empty dashboardURL}">
                    <a class="navbar-brand" href="${dashboardURL}">Arrange Delivery</a>
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
                            <ul class="dropdown-menu" aria-labelledby="inventoryDropdown">
                                <li><a class="dropdown-item" href="/InventoryController">Show Inventory</a></li>
                                <li><a class="dropdown-item" href="/CheckInController">Check In</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="bookingDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Manage Bookings
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="bookingDropdown">
                                <li><a class="dropdown-item" href="/BookingController">Manage Bookings</a></li>
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
            <h2 class="text-center my-4">Arrange</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Reservation ID</th>
                        <th>User Name</th>
                        <th>Reserved From</th>
                        <th>Reserved To</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reservation" items="${reservations}" varStatus="status">
                        <tr>
                            <td>${reservation.reservationID}</td>
                            <td>${reservation.userName}</td>
                            <td>${reservation.reservedFrom}</td>
                            <td>${reservation.reservedTo}</td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="arrangeDelivery('${reservation.reservationID}', '${reservation.toCampus}');">Arrange Delivery</button>
                            </td>
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
                                            <th>Campus Address</th>
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
                                                <td>${reservation.address}</td>
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

        <!-- Arrange Delivery Modal -->
        <div class="modal fade" id="arrangeDeliveryModal" tabindex="-1" aria-labelledby="arrangeDeliveryModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="arrangeDeliveryModalLabel">Arrange Delivery</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="ArrangeDeliveryController" method="post">
                            <input type="hidden" name="action" value="addDelivery">
                            <div class="mb-3">
                                <label for="modalReservationID" class="form-label">Reservation ID:</label>
                                <input type="text" id="modalReservationID" name="reservationID" class="form-control" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="toCampus" class="form-label">To Campus:</label>
                                <input type="text" id="toCampus" name="toCampus" class="form-control" readonly>
                            </div>
                            <input type="submit" value="Confirm Delivery" class="btn btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
