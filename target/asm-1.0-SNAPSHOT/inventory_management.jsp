
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inventory Management</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>
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
                padding-right: 18px
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
    </head>Inventory Management
    <body>
         <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container nav-container">
                 <c:if test="${not empty dashboardURL}">
                    <a class="navbar-brand" href="${dashboardURL}">Inventory Management</a>
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

        <div class="container mt-5 container2">

            <h2 class="text-center my-4">Inventory Managementt</h2>

            <button id="addBtn" class="btn btn-primary mt-3" data-bs-toggle="modal" data-bs-target="#addModal">Add New Item</button>

            <table class="table table-striped mt-3">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Condition</th>
                        <th>Availability</th>
                        <th>Exclusive For Staff</th>
                        <th>Orginal Campus</th>
                        <th>Current Campus</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equipment" items="${equipmentList}">
                        <tr>
                            <td>${equipment.equipmentID}</td>
                            <td>${equipment.name}</td>
                            <td>${equipment.description}</td>
                            <td>${equipment.condition}</td>
                            <td>${equipment.available}</td>
                            <td>${equipment.exclusiveForStaff}</td>
                            <td>${equipment.campusName}</td>
                            <td>${equipment.currentCampus}</td>
                            <td>
                                <button type="button" class="btn btn-warning" onclick="showEditModal({
                                            equipmentName: '${equipment.name}',
                                            equipmentID: '${equipment.equipmentID}',
                                            description: '${equipment.description}',
                                            available: '${equipment.available}',
                                            currentCampus: '${equipment.currentCampus}',
                                            campusName: '${equipment.campusName}',
                                            condition: '${equipment.condition}',
                                            exclusiveForStaff: '${equipment.exclusiveForStaff}'
                                        });">Edit</button>
                            </td>
                            <td><button class="btn btn-danger" onclick="showDeleteModal('${equipment.equipmentID}');">Delete</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Add Item Modal -->
            <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addModalLabel">Add New Item</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="addForm" action="/InventoryController" method="post">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="equipmentID" id="addEquipmentID" value="${newEquipmentID}" readonly>
                                <div class="mb-3">
                                    <label for="addName" class="form-label">Name:</label>
                                    <input type="text" class="form-control" name="name" id="addName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="addDescription" class="form-label">Description:</label>
                                    <textarea class="form-control" id="addDescription" name="description" rows="4"></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="addCondition" class="form-label">Condition:</label>
                                    <select class="form-select" id="addCondition" name="condition">
                                        <option value="New">New</option>
                                        <option value="Good">Good</option>
                                        <option value="Fair">Fair</option>
                                        <option value="Poor">Poor</option>
                                        <option value="Out of Service">Out of Service</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="addAvailable" class="form-label">Available:</label>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="available" id="addAvailableYes" value="Yes">
                                        <label class="form-check-label" for="addAvailableYes">
                                            Yes
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="available" id="addAvailableNo" value="No">
                                        <label class="form-check-label" for="addAvailableNo">
                                            No
                                        </label>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">Add Item</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Edit Item Modal -->
            <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editModalLabel">Edit Item</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="/InventoryController" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" id="editEquipmentID" name="equipmentID">

                                <div class="mb-3">
                                    <label for="editName" class="form-label">Name:</label>
                                    <input type="text" class="form-control" id="editName" name="name" readonly required>
                                </div>

                                <div class="mb-3">
                                    <label for="editDescription" class="form-label">Description:</label>
                                    <textarea class="form-control" id="editDescription" name="description" rows="4"></textarea>
                                </div>

                                <div class="mb-3">
                                    <label for="editCondition" class="form-label">Condition:</label>
                                    <select class="form-select" id="editCondition" name="condition">
                                        <option value="New">New</option>
                                        <option value="Good">Good</option>
                                        <option value="Fair">Fair</option>
                                        <option value="Poor">Poor</option>
                                        <option value="Out of Service">Out of Service</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="editAvailable" class="form-label">Available:</label>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="available" id="editAvailableYes" value="Yes">
                                        <label class="form-check-label" for="editAvailableYes">Yes</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="available" id="editAvailableNo" value="No">
                                        <label class="form-check-label" for="editAvailableNo">No</label>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="editCurrentCampus" class="form-label">Current Campus:</label>
                                    <select class="form-select" id="editCurrentCampus" name="currentCampus">
                                        <option value="Chai Wan">Chai Wan</option>
                                        <option value="Lee Wai Lee">Lee Wai Lee</option>
                                        <option value="Sha Tin">Sha Tin</option>
                                        <option value="Tuen Mun">Tuen Mun</option>
                                        <option value="Tsing Yi">Tsing Yi</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="editExclusiveForStaff" class="form-label">Exclusive for Staff:</label>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="exclusiveForStaff" id="editExclusiveForStaffYes" value="Yes">
                                        <label class="form-check-label" for="editExclusiveForStaffYes">Yes</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="exclusiveForStaff" id="editExclusiveForStaffNo" value="No">
                                        <label class="form-check-label" for="editExclusiveForStaffNo">No</label>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-primary">Update Item</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>


            <!-- Delete Item Modal -->
            <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteModalLabel">Confirm Delete</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to delete this item?
                        </div>
                        <div class="modal-footer">
                            <form action="/InventoryController" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" id="deleteEquipmentID" name="equipmentID">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {

                var addModal = new bootstrap.Modal(document.getElementById('addModal'));
                var editModal = new bootstrap.Modal(document.getElementById('editModal'));
                var deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));


                document.getElementById('addBtn').addEventListener('click', function () {
                    addModal.show();
                });


                window.showEditModal = function (data) {
                    document.getElementById('editName').value = data.equipmentName;
                    document.getElementById('editEquipmentID').value = data.equipmentID;
                    document.getElementById('editDescription').value = data.description;
                    document.querySelectorAll('[name="available"]').forEach((radio) => {
                        if (radio.value === data.available) {
                            radio.checked = true;
                        }
                    });
                    document.getElementById('editCurrentCampus').value = data.currentCampus;
                    document.getElementById('editCondition').value = data.condition;
                    document.querySelector('[name="exclusiveForStaff"][value="' + data.exclusiveForStaff + '"]').checked = true;


                    editModal.show();
                };

                window.showDeleteModal= function (equipmentID) {

                    var deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));


                    var deleteEquipmentIDInput = document.getElementById('deleteEquipmentID');
                    deleteEquipmentIDInput.value = equipmentID;


                    deleteModal.show();
                }


                document.querySelectorAll('.deleteButton').forEach(function (button) {
                    button.addEventListener('click', function () {
                        document.getElementById('deleteEquipmentID').value = this.dataset.equipmentId;
                        deleteModal.show();
                    });
                });


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
            });
        </script>



    </body>
</html>
