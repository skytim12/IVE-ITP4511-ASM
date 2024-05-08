<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check In</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
                    <a class="navbar-brand" href="${dashboardURL}">Check In</a>
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

            <h2 class="text-center my-4">Check In</h2>

            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Delivery ID</th>
                            <th>User Name</th>
                            <th>From Campus</th>
                            <th>To Campus</th>
                            <th>Status</th>
                            <th>Actions</th>
                            <th>Toggle Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="delivery" items="${deliveries}" varStatus="status">
                            <tr >
                                <td>${delivery.deliveryID}</td>
                                <td>${delivery.userName}</td>
                                <td>${delivery.fromCampus}</td>
                                <td>${delivery.toCampus}</td>
                                <td>${delivery.status}</td>

                                <td>
                                    <form action="/CheckInController" method="post">
                                        <input type="hidden" name="deliveryID" value="${delivery.deliveryID}">
                                        <input type="hidden" name="reservationID" value="${delivery.reservationID}">
                                        <input type="hidden" name="action" value="checkin">
                                        <button type="submit" class="btn btn-primary">Check In</button>
                                    </form>
                                </td>
                                <td><button type="button" class="btn btn-info" onclick="toggleDetails(${status.index});">Toggle Details</button></td>
                            </tr>
                            <tr id="details${status.index}" class="collapse">
                                <td colspan="7">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Description</th>
                                                <th>Campus Name</th>
                                                <th>Current Campus</th>
                                                <th>Condition</th>
                                                <th>Exclusive For Staff</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="equipment" items="${delivery.equipmentList}">
                                                <tr>
                                                    <td>${equipment.name}</td>
                                                    <td>${equipment.description}</td>
                                                    <td>${equipment.campusName}</td>
                                                    <td>${equipment.currentCampus}</td>
                                                    <td>${equipment.condition}</td>
                                                    <td>${equipment.exclusiveForStaff}</td>
                                                    <td>
                                                        <button class="btn btn-danger" onclick="openReportModal('${delivery.reservationID}', '${equipment.equipmentID}', '${equipment.name}')">Report Damage</button>
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
        </div>

       
        <div class="modal fade" id="reportDamageModal" tabindex="-1" aria-labelledby="reportDamageModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="reportDamageModalLabel">Report Damage</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="/CheckInController" method="post">
                            <input type="hidden" name="action" value="report">
                            <input type="hidden" name="reservationID" id="modalReservationID">
                            <div class="mb-3">
                                <label for="modalEquipmentID" class="form-label">Equipment ID:</label>
                                <input type="text" class="form-control" id="modalEquipmentID" name="equipmentID" readonly>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Equipment Name:</label>
                                <input type="text" class="form-control" id="modalEquipmentName" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description of Damage:</label>
                                <textarea class="form-control" id="description" name="description" rows="4" required></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Submit Report</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <<script>document.addEventListener('DOMContentLoaded', function () {

                window.toggleDetails = function (rowId) {
                    var detailsRow = document.getElementById('details' + rowId);
                    if (detailsRow.style.display === 'none') {
                        detailsRow.style.display = 'table-row';
                    } else {
                        detailsRow.style.display = 'none';
                    }
                };


                window.openReportModal = function (reservationID, equipmentID, equipmentName) {
                    var modal = new bootstrap.Modal(document.getElementById('reportDamageModal'));
                    document.getElementById('modalEquipmentID').value = equipmentID;
                    document.getElementById('modalEquipmentName').value = equipmentName;
                    document.getElementById('modalReservationID').value = reservationID;
                    modal.show();
                };


                window.onclick = function (event) {
                    var modal = document.getElementById('reportDamageModal');
                    if (event.target === modal) {
                        modal.hide();
                    }
                };


                window.showSuccessMessage = function (message) {
                    alert(message);
                };


                window.showErrorMessage = function (message) {
                    alert(message);
                };


            <% if (request.getAttribute("successMessage") != null) { %>
                showSuccessMessage("<%= request.getAttribute("successMessage") %>");
            <% } %>


            <% if (request.getAttribute("errorMessage") != null) { %>
                showErrorMessage("<%= request.getAttribute("errorMessage") %>");
            <% } %>
            });
        </script>
    </body>
</html>
