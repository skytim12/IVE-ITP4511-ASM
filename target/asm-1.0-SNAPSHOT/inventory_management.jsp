<%-- 
    Document   : inventory_management
    Created on : 2024年4月8日, 上午6:33:08
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Inventory Management</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="js/modal-script.js" defer></script>
    </head>
    <body>
        <h1>Inventory Management</h1>

        <nav>
            <ul>
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="/InventoryController">Show Inventory</a></li>

            </ul>
            <button id="addBtn" onclick="showAddModal()">Add New Item</button>      
            <table>
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
                                <button type="button" onclick="showEditModal({
                                            equipmentID: '${equipment.equipmentID}',
                                            description: '${equipment.description}',
                                            available: '${equipment.available}',
                                            currentCampus: '${equipment.currentCampus}',
                                            campusName: '${equipment.campusName}',
                                            condition: '${equipment.condition}',
                                            exclusiveForStaff: '${equipment.exclusiveForStaff}'
                                        });">Edit</button>
                            </td>
                            <td><button onclick="showDeleteModal('${equipment.equipmentID}');">Delete</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Add Item Modal -->
            <div id="addModal" class="modal">
                <div class="modal-content">
                    <span class="close" onclick="closeAddModal()">&times;</span>
                    <form id="addForm" action="/InventoryController" method="post">
                        <input type="hidden" name="action" value="add">

                        <!-- Equipment ID input field -->
                        <label for="addEquipmentID">Equipment ID:</label>
                        <input type="text" name="equipmentID" id="addEquipmentID" value="${newEquipmentID}" readonly><br>

                        <!-- Name input field -->
                        <label for="addName">Name:</label>
                        <input type="text" name="name" id="addName"><br>

                        <!-- Description input field -->
                        <label for="addDescription">Description:</label>
                        <textarea id="addDescription" name="description" rows="4" cols="50"></textarea><br>

                        <!-- Available radio buttons -->
                        <fieldset>
                            <legend>Available:</legend>
                            <label><input type="radio" name="available" value="Yes" id="addAvailableYes"> Yes</label>
                            <label><input type="radio" name="available" value="No" id="addAvailableNo"> No</label>
                        </fieldset><br>

                        <!-- Condition select field -->
                        <label for="addCondition">Condition:</label>
                        <select id="addCondition" name="condition">
                            <option value="New">New</option>
                            <option value="Good">Good</option>
                            <option value="Fair">Fair</option>
                            <option value="Poor">Poor</option>
                            <option value="Out of Service">Out of Service</option>
                        </select><br>

                        <!-- Exclusive for Staff radio buttons -->
                        <fieldset>
                            <legend>Exclusive for Staff:</legend>
                            <label><input type="radio" name="exclusiveForStaff" value="Yes" id="addExclusiveForStaffYes"> Yes</label>
                            <label><input type="radio" name="exclusiveForStaff" value="No" id="addExclusiveForStaffNo"> No</label>
                        </fieldset><br>

                        <!-- Submit button -->
                        <input type="submit" value="Add Item">
                    </form>
                </div>
            </div>


            <!-- Edit Item Modal -->
            <div id="editModal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <form action="/InventoryController" method="post">
                        <input type="hidden" name="action" value="update">

                        <label for="editEquipmentID">Equipment ID:</label>
                        <input type="text" name="equipmentID" id="editEquipmentID" readonly><br>

                        <label for="editDescription">Description:</label>
                        <textarea id="editDescription" name="description" rows="4" cols="50"></textarea><br>

                        <fieldset>
                            <legend>Available:</legend>
                            <label><input type="radio" name="available" value="Yes" id="editAvailableYes"> Yes</label>
                            <label><input type="radio" name="available" value="No" id="editAvailableNo"> No</label>
                        </fieldset><br>

                        <label for="editCurrentCampus">Current Campus:</label>
                        <select id="editCurrentCampus" name="currentCampus">
                            <option value="Chai Wan">Chai Wan</option>
                            <option value="Lee Wai Lee">Lee Wai Lee</option>
                            <option value="Sha Tin">Sha Tin</option>
                            <option value="Tuen Mun">Tuen Mun</option>
                            <option value="Tsing Yi">Tsing Yi</option>
                        </select><br>

                        <label for="editOriginalCampus">Original Campus:</label>
                        <input type="text" id="editOriginalCampus" name="originalCampus" readonly><br>

                        <label for="editCondition">Condition:</label>
                        <select id="editCondition" name="condition">
                            <option value="New">New</option>
                            <option value="Good">Good</option>
                            <option value="Fair">Fair</option>
                            <option value="Poor">Poor</option>
                            <option value="Out of Service">Out of Service</option>
                        </select><br>

                        <fieldset>
                            <legend>Exclusive for Staff:</legend>
                            <label><input type="radio" name="exclusiveForStaff" value="Yes" id="editExclusiveForStaffYes"> Yes</label>
                            <label><input type="radio" name="exclusiveForStaff" value="No" id="editExclusiveForStaffNo"> No</label>
                        </fieldset><br>

                        <input type="submit" value="Update Item">
                    </form>
                </div>
            </div>

            <!-- Delete Item Modal -->
            <div id="deleteModal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <p>Are you sure you want to delete this item?</p>
                    <form action="/InventoryController" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" id="deleteEquipmentID" name="equipmentID">
                        <input type="submit" value="Delete Item">
                    </form>
                </div>
            </div>


            <script>
                // Function to show a success message
                function showSuccessMessage(message) {
                    alert(message);
                }

                // Function to show an error message
                function showErrorMessage(message) {
                    alert(message);
                }

                // Check for success message from server and display
                <% if (request.getAttribute("successMessage") != null) { %>
                showSuccessMessage("<%= request.getAttribute("successMessage") %>");
                <% } %>

                // Check for error message from server and display
                <% if (request.getAttribute("errorMessage") != null) { %>
                showErrorMessage("<%= request.getAttribute("errorMessage") %>");
                <% } %>
            </script>
    </body>
</html>