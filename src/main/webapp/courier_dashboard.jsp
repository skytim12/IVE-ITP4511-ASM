
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Courier Dashboard</title>
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
                <a class="navbar-brand" href="#">Courier Operations</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="/DeliveryController">View Delivery Schedule</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/UpdateDeliveryStatusController">Update Delivery Status</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
