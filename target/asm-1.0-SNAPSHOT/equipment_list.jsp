
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Equipment List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>
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
                <a class="navbar-brand" href="#">Equipment List</a>
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
            <h2 class="text-center my-4">Equipment List</h2>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Available</th>
                        <th>Initial Campus</th>
                        <th>Current Campus</th>
                        <th>Condition</th>
                        <th>Description</th>
                        <th>Total</th>
                        <th>Staff Only</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equipment" items="${equipmentList}">
                        <tr>
                            <td>${equipment.name}</td>
                            <td>${equipment.available}</td>
                            <td>${equipment.campusName}</td>
                            <td>${equipment.currentCampus}</td>
                            <td>${equipment.condition}</td>
                            <td>${equipment.description}</td>
                            <td>${equipment.totalQuantity}</td>
                            <td>${equipment.exclusiveForStaff == 'Yes' ? 'Yes' : 'No'}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${equipment.available == 'Yes'}">
                                        <button type="button" class="btn btn-primary cartBtn"
                                                data-name="${equipment.name}"
                                                data-available="${equipment.available}"
                                                data-campus="${equipment.campusName}"
                                                data-condition="${equipment.condition}"
                                                data-description="${equipment.description}"
                                                data-total="${equipment.totalQuantity}"
                                                data-exclusive="${equipment.exclusiveForStaff}"
                                                data-quantity="1">Add to Cart</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-secondary wishlistBtn"
                                                data-id="${equipment.equipmentIDs}"
                                                data-name="${equipment.name}"
                                                data-available="${equipment.available}"
                                                data-campus="${equipment.campusName}"
                                                data-currentcampus="${equipment.currentCampus}"
                                                data-condition="${equipment.condition}"
                                                data-exclusive="${equipment.exclusiveForStaff}"
                                                data-description="${equipment.description}">Add to Wishlist</button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="container">
            <h2 class="mt-4">Your Cart</h2>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Campus Name</th>
                        <th>Condition</th>
                        <th>Reserved From</th>
                        <th>Reserved To</th>
                        <th>Quantity</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cart}">
                        <tr>
                            <td>${item.name}</td>
                            <td>${item.campusName}</td>
                            <td>${item.condition}</td>
                            <td>${item.reservedFrom}</td>
                            <td>${item.reservedTo}</td>
                            <td>${item.quantity}</td>
                            <td>
                                <form action="EquipmentController" method="post">
                                    <input type="hidden" name="action" value="removeFromCart"/>
                                    <input type="hidden" name="name" value="${item.name}"/>
                                    <input type="hidden" name="campus" value="${item.campusName}"/>
                                    <input type="hidden" name="condition" value="${item.condition}"/>
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty cart}">
                <form action="EquipmentController" method="post">
                    <input type="hidden" name="action" value="confirmCart" />
                    <button type="submit" class="btn btn-success">Confirm</button>
                </form>
            </c:if>
            <c:if test="${empty cart}">
                <p>Your cart is empty.</p>
            </c:if>
        </div>


        <!-- Modal for Wishlist -->
        <div class="modal fade" id="wishlistModal" tabindex="-1" aria-labelledby="wishlistModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="wishlistModalLabel">Add to Wishlist</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="EquipmentController" method="post">
                            <input type="hidden" name="action" value="addToWishlist" />
                            <div class="mb-3">
                                <label for="equipmentID" class="form-label">Equipment ID:</label>
                                <select class="form-select" name="equipmentID" id="equipmentID"></select>
                            </div>
                            <div class="mb-3">
                                <label for="name" class="form-label">Name:</label>
                                <input type="text" class="form-control" id="name" name="name" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="available" class="form-label">Available:</label>
                                <input type="text" class="form-control" id="available" name="available" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="campusName" class="form-label">Campus Name:</label>
                                <input type="text" class="form-control" id="campusName" name="campusName" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="cartCurrentCampus" class="form-label">Current Campus:</label>
                                <input type="text" class="form-control" id="cartCurrentCampus" name="currentCampus" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="condition" class="form-label">Condition:</label>
                                <input type="text" class="form-control" id="condition" name="condition" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="exclusiveForStaff" class="form-label">Exclusive for Staff:</label>
                                <input type="text" class="form-control" id="exclusiveForStaff" name="exclusiveForStaff" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description:</label>
                                <textarea class="form-control" id="description" name="description" readonly></textarea>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add to Wishlist</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>



        <!-- Modal for Cart -->
        <div class="modal fade" id="cartModal" tabindex="-1" aria-labelledby="cartModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="cartModalLabel">Add to Cart</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="cartForm" action="EquipmentController" method="post">
                            <input type="hidden" name="action" value="addToCart" />
                            <div class="mb-3">
                                <label for="cartName" class="form-label">Name:</label>
                                <input type="text" class="form-control" id="cartName" name="name" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="cartAvailable" class="form-label">Available:</label>
                                <input type="text" class="form-control" id="cartAvailable" name="available" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="cartCampus" class="form-label">Campus:</label>
                                <input type="text" class="form-control" id="cartCampus" name="campus" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="cartCondition" class="form-label">Condition:</label>
                                <input type="text" class="form-control" id="cartCondition" name="condition" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="cartDescription" class="form-label">Description:</label>
                                <textarea class="form-control" id="cartDescription" name="description" readonly></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="cartReservedFrom" class="form-label">Reserved From:</label>
                                <input type="date" class="form-control" id="cartReservedFrom" name="reservedFrom" required>
                            </div>
                            <div class="mb-3">
                                <label for="cartReservedTo" class="form-label">Reserved To:</label>
                                <input type="date" class="form-control" id="cartReservedTo" name="reservedTo" required>
                            </div>
                            <div class="mb-3">
                                <label for="cartTotal" class="form-label">Total Available:</label>
                                <input type="text" class="form-control" id="cartTotal" name="total" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="cartExclusiveForStaff" class="form-label">Exclusive for Staff:</label>
                                <input type="text" class="form-control" id="cartExclusiveForStaff" name="exclusiveForStaff" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="cartQuantity" class="form-label">Quantity:</label>
                                <input type="number" class="form-control" id="cartQuantity" name="quantity" min="1">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" form="cartForm">Add to Cart</button>
                    </div>
                </div>
            </div>
        </div>



        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var wishlistModal = new bootstrap.Modal(document.getElementById('wishlistModal'));
                var cartModal = new bootstrap.Modal(document.getElementById('cartModal'));

                // Attach click event to Wishlist buttons
                document.querySelectorAll('.wishlistBtn').forEach(btn => {
                    btn.addEventListener('click', function () {
                        // Populate the wishlist form fields
                        var equipmentIDs = this.getAttribute('data-id').split(',');
                        var select = document.getElementById('equipmentID');
                        select.innerHTML = '';

                        equipmentIDs.forEach(function (id) {
                            var option = document.createElement('option');
                            option.value = id.trim();
                            option.text = id.trim();
                            select.appendChild(option);
                        });

                        document.getElementById('name').value = this.dataset.name;
                        document.getElementById('available').value = this.dataset.available;
                        document.getElementById('campusName').value = this.dataset.campus;
                        document.getElementById('cartCurrentCampus').value = this.dataset.currentcampus;
                        document.getElementById('condition').value = this.dataset.condition;
                        document.getElementById('exclusiveForStaff').value = this.dataset.exclusive;
                        document.getElementById('description').value = this.dataset.description;

                        wishlistModal.show();
                    });
                });

                // Attach click event to Cart buttons
                document.querySelectorAll('.cartBtn').forEach(btn => {
                    btn.addEventListener('click', function () {
                        // Populate the cart form fields
                        document.getElementById('cartName').value = this.dataset.name;
                        document.getElementById('cartAvailable').value = this.dataset.available;
                        document.getElementById('cartCampus').value = this.dataset.campus;
                        document.getElementById('cartCondition').value = this.dataset.condition;
                        document.getElementById('cartDescription').value = this.dataset.description;
                        document.getElementById('cartTotal').value = this.dataset.total;
                        document.getElementById('cartExclusiveForStaff').value = this.dataset.exclusive;

                        // Set the minimum date for reservation to today's date
                        const today = new Date().toISOString().split('T')[0];
                        document.getElementById('cartReservedFrom').setAttribute('min', today);
                        document.getElementById('cartReservedTo').setAttribute('min', today);

                        cartModal.show();
                    });
                });


                window.addEventListener('click', function (event) {
                    if (event.target === document.getElementById('wishlistModal')) {
                        wishlistModal.hide();
                    }
                    if (event.target === document.getElementById('cartModal')) {
                        cartModal.hide();
                    }
                });
            });


        </script>



    </body>
</html>
