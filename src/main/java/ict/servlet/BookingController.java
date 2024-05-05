/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.ReservationBean;
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

@WebServlet(name = "BookingController", urlPatterns = {"/BookingController"})
public class BookingController extends HttpServlet {

    private AsmDB db;

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
        String action = request.getParameter("action");
        String reservationID = request.getParameter("reservationID");
        try {
            switch (action) {
                case "Accept":
                    updateBorrowingRecordStatus(reservationID, "success");
                    request.setAttribute("successMessage", "Reservation accepted successfully.");
                    break;
                case "Decline":
                    updateBorrowingRecordStatus(reservationID, "fail");
                    request.setAttribute("successMessage", "Reservation declined successfully.");
                    break;
                default:
                    request.setAttribute("errorMessage", "Invalid action.");
                    break;
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
        }
        processRequest(request, response);
    }

    private void updateBorrowingRecordStatus(String reservationID, String status) throws SQLException, IOException {
        db.updateBorrowingRecordStatus(reservationID, status);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        
        if (user == null) {
            request.setAttribute("errorMessage", "You must be logged in to access this page.");
            request.getRequestDispatcher("/login_page.jsp").forward(request, response);
            return;
        }
        
        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);
        
        try {
            List<ReservationBean> reservations = db.fetchWaitingReservations(user.getCampusName());
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("/booking_management.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
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
                        return "/staff_dashboard.jsp";
                    default:
                        return "/UserDashboard";
                }
            }
        }
        return "";
    }
}
