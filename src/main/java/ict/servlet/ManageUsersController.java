package ict.servlet;

import ict.bean.UserBean;
import ict.db.AsmDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ManageUsersController", urlPatterns = {"/ManageUsersController"})
public class ManageUsersController extends HttpServlet {

    private AsmDB db;
    private final static Logger LOGGER = Logger.getLogger(ManageUsersController.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        listUsers(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "addUser":
                addUser(request, response);
                break;
            case "editUser":
                editUser(request, response);
                break;
            case "deleteUser":
                deleteUser(request, response);
                break;
            case "changePassword":
                changePassword(request, response);
                break;
            default:
                request.setAttribute("errorMessage", "Invalid action.");
                return;
        }
        listUsers(request, response);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);
        try {
            List<UserBean> users = db.getAllUsersByCampus(user.getCampusName());
            request.setAttribute("users", users);
            request.getRequestDispatcher("/manageUsers.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error when listing users: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/manageUsers.jsp").forward(request, response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBean user = new UserBean();
        user.setUserID(request.getParameter("userID"));
        user.setUsername(request.getParameter("username"));
        user.setPassword(db.hashPassword(request.getParameter("password")));
        user.setFullName(request.getParameter("fullName"));
        user.setRole(request.getParameter("role"));
        user.setCampusName(request.getParameter("campusName"));

        try {
            boolean success = db.addUser(user);
            if (success) {
                request.setAttribute("successMessage", "User successfully added.");
                listUsers(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to add user.");
            }
            request.getRequestDispatcher("/manageUsers.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBean user = new UserBean();
        user.setUserID(request.getParameter("userID"));
        user.setFullName(request.getParameter("fullName"));
        user.setRole(request.getParameter("role"));
        user.setCampusName(request.getParameter("campusName"));

        try {
            boolean success = db.updateUserDetails(user);
            if (success) {
                request.setAttribute("successMessage", "User details successfully updated.");
                listUsers(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to update user details.");
            }

            request.getRequestDispatcher("/manageUsers.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());

            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");

        try {
            boolean success = db.deleteUser(userID);
            if (success) {
                request.setAttribute("successMessage", "User successfully deleted.");
                listUsers(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to delete user.");
            }
            request.getRequestDispatcher("/manageUsers.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String newPassword = request.getParameter("newPassword");

        if (newPassword == null || newPassword.isEmpty()) {
            request.setAttribute("errorMessage", "New password cannot be empty.");
            request.getRequestDispatcher("/manageUsers.jsp").forward(request, response);
            return;
        }

        try {

            String hashedPassword = db.hashPassword(newPassword);
            boolean success = db.changeUserPassword(userID, hashedPassword);
            if (success) {
                request.setAttribute("successMessage", "Password successfully changed.");
                listUsers(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to change password.");
            }
            request.getRequestDispatcher("/manageUsers.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private String getDashboardURL(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserBean user = (UserBean) session.getAttribute("userBean");
            if (user != null) {
                switch (user.getRole()) {
                    case "AdminTechnician":
                        return "/AdminController";
                    case "Technician":
                        return "/TechDashboard";
                    case "Courier":
                        return "/CourierControllor";
                    case "Staff":
                        return "/StaffDashboard";
                    default:
                        return "/UserDashboard";
                }
            }
        }
        return "";
    }

}
