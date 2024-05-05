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

@WebServlet(name = "ArrangeDeliveryController", urlPatterns = {"/ArrangeDeliveryController"})
public class ArrangeDeliveryController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the database object using context parameters
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

        if ("addDelivery".equals(action)) {
            try {
                addDelivery(request);
                request.setAttribute("message", "Delivery arranged successfully.");
            } catch (SQLException ex) {
                request.setAttribute("errorMessage", "Error arranging delivery: " + ex.getMessage());
            }
        }
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        if (user == null) {
            request.setAttribute("errorMessage", "You must be logged in to access this page.");
            request.getRequestDispatcher("/login_page.jsp").forward(request, response);
            return;
        }

        try {
            List<ReservationBean> reservations = db.fetchReservationsForDelivery(user.getCampusName());
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("/arrange_delivery.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
        }

    }

    private void addDelivery(HttpServletRequest request) throws SQLException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        int reservationID = Integer.parseInt(request.getParameter("reservationID"));
        String fromCampus = user.getCampusName();
        String toCampus = request.getParameter("toCampus");
        String courierID = request.getParameter("courierID");

        db.addDelivery(reservationID, fromCampus, toCampus, courierID, "Scheduled");
    }

}
