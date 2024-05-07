<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Users</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <div class="container mt-4">
            <h1>User Management</h1>
            <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addUserModal">Add New User</button>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Role</th>
                        <th>Campus</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}" varStatus="status">
                        <tr>
                            <td>${user.userID}</td>
                            <td>${user.username}</td>
                            <td>${user.fullName}</td>
                            <td>${user.role}</td>
                            <td>${user.campusName}</td>
                            <td>
                                <!-- Trigger modal with JavaScript -->
                                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#editUserModal${user.userID}">Edit</button>
                                <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#changePasswordModal${user.userID}" data-user-id="${user.userID}">Change Password</button>
                                <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteUserModal${user.userID}">Delete</button>
                            </td>
                        </tr>
                        <!-- Edit User Modal -->
                    <div class="modal fade" id="editUserModal${user.userID}" tabindex="-1" aria-labelledby="editUserModalLabel${user.userID}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editUserModalLabel${user.userID}">Edit User</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="/ManageUsersController" method="post">
                                        <input type="hidden" name="action" value="editUser">
                                        <input type="hidden" name="userID" value="${user.userID}">

                                        <div class="mb-3">
                                            <label for="fullName${user.userID}" class="form-label">Full Name:</label>
                                            <input type="text" class="form-control" id="fullName${user.userID}" name="fullName" value="${user.fullName}" required>
                                        </div>

                                        <div class="mb-3">
                                            <label for="role${user.userID}" class="form-label">Role:</label>
                                            <select class="form-select" id="role${user.userID}" name="role">
                                                <option value="GeneralUser">User</option>
                                                <option value="Technician">Technician</option>
                                                <option value="Courier">Courier</option>
                                                <option value="Admin">Admin</option>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="campusName${user.userID}" class="form-label">Campus:</label>
                                            <select class="form-select" id="campusName${user.userID}" name="campusName">
                                                <option value="Chai Wan">Chai Wan</option>
                                                <option value="Lee Wai Lee">Lee Wai Lee</option>
                                                <option value="Sha Tin">Sha Tin</option>
                                                <option value="Tuen Mun">Tuen Mun</option>
                                                <option value="Tsing Yi">Tsing Yi</option>
                                            </select>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <button type="submit" class="btn btn-primary">Update User</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Delete User Modal -->
                    <div class="modal fade" id="deleteUserModal${user.userID}" tabindex="-1" aria-labelledby="deleteUserModalLabel${user.userID}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="deleteUserModalLabel${user.userID}">Confirm Delete</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    Are you sure you want to delete ${user.username}?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    <form action="/ManageUsersController" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="deleteUser">
                                        <input type="hidden" name="userID" value="${user.userID}">
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- Change Password Modal -->
                    <div class="modal fade" id="changePasswordModal${user.userID}" tabindex="-1" aria-labelledby="changePasswordModalLabel${user.userID}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="changePasswordModalLabel${user.userID}">Change Password for ${user.username}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="/ManageUsersController" method="post">
                                        <input type="hidden" name="action" value="changePassword">
                                        <input type="hidden" name="userID" id="changePasswordUserID${user.userID}" value="${user.userID}">

                                        <div class="mb-3">
                                            <label for="newPassword${user.userID}" class="form-label">New Password:</label>
                                            <input type="password" class="form-control" id="newPassword${user.userID}" name="newPassword" required>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <button type="submit" class="btn btn-primary">Update Password</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Add User Modal -->
        <div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addUserModalLabel">Add New User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="/ManageUsersController" method="post">
                            <input type="hidden" name="action" value="addUser">
                            <div class="mb-3">
                                <label for="newUserID" class="form-label">User ID:</label>
                                <input type of="text" class="form-control" id="newUserID" name="userID" required>
                            </div>
                            <div class="mb-3">
                                <label for="newUsername" class="form-label">Username:</label>
                                <input type="text" class="form-control" id="newUsername" name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="newPassword" class="form-label">Password:</label>
                                <input type="password" class="form-control" id="newPassword" name="password" required>
                            </div>
                            <div class="mb-3">
                                <label for="newFullName" class="form-label">Full Name:</label>
                                <input type="text" class="form-control" id="newFullName" name="fullName" required>
                            </div>
                            <div class="mb-3">
                                <label for="newRole" class="form-label">Role:</label>
                                <select class="form-select" id="newRole" name="role">
                                    <option value="GeneralUser">User</option>
                                    <option value="Technician">Technician</option>
                                    <option value="Courier">Courier</option>
                                    <option value="AdminTechnician">Admin Technician</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="newCampusName" class="form-label">Campus:</label>
                                <select class="form-select" id="newCampusName" name="campusName">
                                    <option value="Chai Wan">Chai Wan</option>
                                    <option value="Lee Wai Lee">Lee Wai Lee</option>
                                    <option value="Sha Tin">Sha Tin</option>
                                    <option value="Tuen Mun">Tuen Mun</option>
                                    <option value="Tsing Yi">Tsing Yi</option>
                                </select>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add User</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>






        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Function to initialize modals
                const initializeModal = (modalId) => {
                    return new bootstrap.Modal(document.getElementById(modalId), {
                        keyboard: false
                    });
                };

                // Handler for Edit buttons
                document.querySelectorAll('.btn-success').forEach(button => {
                    button.addEventListener('click', function () {
                        const userId = this.getAttribute('data-bs-target').slice(14); // extracts userID from the modal target id
                        const modal = initializeModal(`editUserModal${userId}`);
                        // Here you would set any necessary data to your modal fields
                        document.getElementById(`editUserID${userId}`).value = userId;
                        modal.show();
                    });
                });

                // Handler for Change Password buttons
                document.querySelectorAll('.change-password-button').forEach(button => {
                    button.addEventListener('click', function () {
                        var userID = this.getAttribute('data-user-id');
                        var modalID = `changePasswordModal${userID}`;
                        var modal = new bootstrap.Modal(document.getElementById(modalID), {
                            keyboard: false
                        });
                        // Correctly set the userID in the modal's form
                        document.getElementById(`changePasswordUserID${userID}`).value = userID;
                        modal.show();
                    });
                });

                // Handler for Delete buttons
                document.querySelectorAll('.btn-danger').forEach(button => {
                    button.addEventListener('click', function () {
                        const userId = this.getAttribute('data-bs-target').slice(15); // extracts userID from the modal target id
                        const modal = initializeModal(`deleteUserModal${userId}`);
                        modal.show();
                    });
                });
            });

        </script>
    </body>
</html>
