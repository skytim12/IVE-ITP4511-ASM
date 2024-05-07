/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ict.db.AsmDB;
import ict.bean.UserBean;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ProfileController", urlPatterns = {"/ProfileController"})
public class ProfileController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        if (user != null) {
            String fullName = request.getParameter("name");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "Passwords do not match or are null.");
            } else {
                try {
                    db.updateUserProfile(user.getUserID(), fullName, newPassword);
                    session.setAttribute("name", fullName);
                    user.setFullName(fullName);
                    request.setAttribute("successMessage", "Profile updated successfully.");

                } catch (SQLException ex) {
                    request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
                }
            }
            processRequest(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);

        if (user != null) {
            request.setAttribute("userProfile", user);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "User not logged in.");
            request.getRequestDispatcher("/login_page.jsp").forward(request, response);
        }
    }

    private String getDashboardURL(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserBean user = (UserBean) session.getAttribute("userBean");
            if (user != null) {
                switch (user.getRole()) {
                    case "Admin":
                        return "/admin_dashboard.jsp";
                    case "Technician":
                        return "/technician_dashboard.jsp";
                    case "Courier":
                        return "/courier_dashboard.jsp";
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
