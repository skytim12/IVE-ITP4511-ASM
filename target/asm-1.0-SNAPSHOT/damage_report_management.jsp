<%-- 
    Document   : damage_report_management
    Created on : 2024年4月8日, 上午6:33:18
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Damage Report Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
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
                <a class="navbar-brand" href="/AdminController">Damage Reportss</a>
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

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="damageDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Reports
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="damageDropdown">
                                <li><a class="dropdown-item" href="/DamageController">Damag Reports</a></li>
                                <li><a class="dropdown-item" href="/CheckOutStatsController">Check-Out Statistics</a></li>
                            </ul>
                        </li>

                        <li class="nav-item"><a class="nav-link" href="/ArrangeDeliveryController">Delivery</a></li>
                        <li class="nav-item"><a class="nav-link" href="/ManageUsersController">Manage Users</a></li>
                        <li class="nav-item"><a class="nav-link" href="main?action=logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container container2 mt-4">
           
           <h2 class="text-center my-4">Damage Reports</h2>

            <table class="table table-striped mt-4">
                <thead>
                    <tr>
                        <th>Report ID</th>
                        <th>Equipment ID</th>
                        <th>Equipment Name</th>
                        <th>Report Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="report" items="${damageReports}" varStatus="status">
                        <tr>
                            <td>${report.reportID}</td>
                            <td>${report.equipmentID}</td>
                            <td>${report.equipmentName}</td>
                            <td>${report.reportDate}</td>
                            <td>
                                <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#detailsModal${report.reportID}">Details and Confirm</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Modals for each report -->
            <c:forEach var="report" items="${damageReports}">
                <div class="modal fade" id="detailsModal${report.reportID}" tabindex="-1" aria-labelledby="detailsModalLabel${report.reportID}" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="detailsModalLabel${report.reportID}">Report Details</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="/DamageController" method="post">
                                    <input type="hidden" name="action" value="confirmDamage">
                                    <input type="hidden" name="reportID" value="${report.reportID}">
                                    <div class="mb-3">
                                        <label for="modalEquipmentID${report.reportID}" class="form-label">Equipment ID:</label>
                                        <input type="text" class="form-control" id="modalEquipmentID${report.reportID}" name="equipmentID" value="${report.equipmentID}" readonly>
                                    </div>

                                    <div class="mb-3">
                                        <label for="modalEquipmentName${report.reportID}" class="form-label">Equipment Name:</label>
                                        <input type="text" class="form-control" id="modalEquipmentName${report.reportID}" name="equipmentName" value="${report.equipmentName}" readonly>
                                    </div>

                                    <div class="mb-3">
                                        <label for="modalReportDate${report.reportID}" class="form-label">Report Date:</label>
                                        <input type="text" class="form-control" id="modalReportDate${report.reportID}" name="reportDate" value="${report.reportDate}" readonly>
                                    </div>

                                    <div class="mb-3">
                                        <label for="modalReportedBy${report.reportID}" class="form-label">Reported By:</label>
                                        <input type="text" class="form-control" id="modalReportedBy${report.reportID}" name="reportedBy" value="${report.reportedByName}" readonly>
                                    </div>

                                    <div class="mb-3">
                                        <label for="modalDescription${report.reportID}" class="form-label">Description of Damage:</label>
                                        <textarea class="form-control" id="modalDescription${report.reportID}" name="description" readonly>${report.description}</textarea>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Equipment Available:</label>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="available" id="availableYes${report.reportID}" value="Yes" ${report.available == 'Yes' ? 'checked' : ''}>
                                            <label class="form-check-label" for="availableYes${report.reportID}">Yes</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="available" id="availableNo${report.reportID}" value="No" ${report.available == 'No' ? 'checked' : ''}>
                                            <label class="form-check-label" for="availableNo${report.reportID}">No</label>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="modalEquipmentCondition${report.reportID}" class="form-label">Equipment Condition:</label>
                                        <select class="form-select" id="modalEquipmentCondition${report.reportID}" name="condition">
                                            <option value="New" ${report.condition == 'New' ? 'selected' : ''}>New</option>
                                            <option value="Good" ${report.condition == 'Good' ? 'selected' : ''}>Good</option>
                                            <option value="Fair" ${report.condition == 'Fair' ? 'selected' : ''}>Fair</option>
                                            <option value="Poor" ${report.condition == 'Poor' ? 'selected' : ''}>Poor</option>
                                            <option value="Out of Service" ${report.condition == 'Out of Service' ? 'selected' : ''}>Out of Service</option>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Confirm Damage</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </body>
</html>
