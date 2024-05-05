package ict.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import ict.db.AsmDB;
import ict.bean.UserBean;
import ict.bean.BorrowingRecordsBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "BorrowingController", urlPatterns = {"/BorrowingController"})
public class BorrowingController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("returnEquipment".equals(action)) {
            try {
                db.returnEquipment(request);
            } catch (SQLException ex) {
                Logger.getLogger(BorrowingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("successMessage", "Equipment returned successfully and reservation status updated.");
        }
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        // Set dashboard URL at the very beginning
        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);

        if (user != null) {
            try {
                List<BorrowingRecordsBean> borrowingRecords = db.fetchBorrowingRecords(user.getUserID());
                request.setAttribute("borrowingRecords", borrowingRecords);
                request.getRequestDispatcher("/borrowing_records.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(BorrowingController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            }
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
