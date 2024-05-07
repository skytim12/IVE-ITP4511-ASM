<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Technician Dashboard</title>
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
                <a class="navbar-brand" href="#">Technician Dashboard</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTech" aria-controls="navbarTech" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarTech">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="/InventoryController">Manage Inventory</a></li>
                        <li class="nav-item"><a class="nav-link" href="/BookingController">Manage Bookings</a></li>
                        <li class="nav-item"><a class="nav-link" href="/ArrangeDeliveryController">Arrange Delivery</a></li>
                        <li class="nav-item"><a class="nav-link" href="/CheckInController">Check In</a></li>
                        <li class="nav-item"><a class="nav-link" href="/ProfileController">Profile</a></li>
                        <li class="nav-item"><a class="nav-link" href="/TechDashboard?action=logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
            <h2 class="text-center my-4">Notification</h2>
            <!-- Activities or relevant information can be listed here -->
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
