<%-- 
    Document   : check_out_stats
    Created on : 2024年5月6日, 上午2:08:39
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Check-Out Statistics</title>
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
                <a class="navbar-brand" href="/AdminController">Statistics</a>
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
        
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-6">
                    <h2 class="text-center">By Equipment</h2>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Equipment ID</th>
                                <th>Name</th>
                                <th>Campus Name</th>
                                <th>Equipment Condition</th>
                                <th>Total Check-Outs</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${equipmentStats}">
                                <tr>
                                    <td>${item.equipmentID}</td>
                                    <td>${item.name}</td>
                                    <td>${item.campusName}</td>
                                    <td>${item.condition}</td>
                                    <td>${item.checkOutCount}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <h2 class="text-center">By Campus</h2>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Campus Name</th>
                                <th>Total Check-Outs</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="campus" items="${campusStats}">
                                <tr>
                                    <td>${campus.campusName}</td>
                                    <td>${campus.totalCheckOuts}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
