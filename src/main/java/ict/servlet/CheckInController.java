/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.DeliveryBean;
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

@WebServlet(name = "CheckInController", urlPatterns = {"/CheckInController"})
public class CheckInController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();

        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("errorMessage", "Action is missing.");
        } else {
            switch (action) {
                case "checkin":
                    handleCheckIn(request, user);
                    break;
                case "report":
                    handleDamageReport(request, user);
                    break;
                default:
                    request.setAttribute("errorMessage", "Invalid action.");
                    break;
            }
        }
        processRequest(request, response);
    }

    private void handleCheckIn(HttpServletRequest request, UserBean user) throws IOException {
        try {
            int reservationID = Integer.parseInt(request.getParameter("reservationID"));
            int deliveryID = Integer.parseInt(request.getParameter("deliveryID"));
            boolean success = db.checkInDelivery(deliveryID, reservationID, user.getCampusName());
            if (success) {
                request.setAttribute("successMessage", "Check-in successful.");
            } else {
                request.setAttribute("errorMessage", "Check-in failed.");
            }
        } catch (SQLException | NumberFormatException ex) {
            request.setAttribute("errorMessage", "Error during check-in: " + ex.getMessage());
        }
    }

    private void handleDamageReport(HttpServletRequest request, UserBean user) throws IOException {
        String equipmentID = request.getParameter("equipmentID");
        String description = request.getParameter("description");
        int reservationID = Integer.parseInt(request.getParameter("reservationID"));
        String reportedBy = user.getUserID();
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp reportDate = new java.sql.Timestamp(today.getTime());
        HttpSession session = request.getSession();

        try {
            boolean success = db.reportDamage(reservationID, equipmentID, description, reportedBy, reportDate,user.getCampusName());
            if (success) {
                request.setAttribute("successMessage", "Damage reported successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to report damage.");
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);

        try {
            List<DeliveryBean> deliveries = db.fetchAllDeliveries(user.getCampusName());
            request.setAttribute("deliveries", deliveries);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
        }
        request.getRequestDispatcher("/checkin.jsp").forward(request, response);
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
