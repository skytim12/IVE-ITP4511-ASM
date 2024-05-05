package ict.servlet;

import ict.db.AsmDB;
import ict.bean.DeliveryBean;
import ict.bean.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "UpdateDeliveryStatusController", urlPatterns = {"/UpdateDeliveryStatusController"})
public class UpdateDeliveryStatusController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        db = new AsmDB(getServletContext().getInitParameter("dbUrl"),
                getServletContext().getInitParameter("dbUser"),
                getServletContext().getInitParameter("dbPassword"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("updateStatus".equals(action)) {
            try {
                updateDeliveryStatus(request);
                request.setAttribute("successMessage", "Delivery status updated successfully.");
            } catch (SQLException ex) {
                request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            }
        }
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        try {
            List<DeliveryBean> deliveries = db.fetchActiveInTransit(user.getCampusName());
            request.setAttribute("deliveries", deliveries);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Error retrieving deliveries: " + ex.getMessage());
        }
        request.getRequestDispatcher("/updateDeliveryStatus.jsp").forward(request, response);
    }

    private void updateDeliveryStatus(HttpServletRequest request) throws SQLException, IOException {
        int deliveryID = Integer.parseInt(request.getParameter("deliveryID"));
        String newStatus = "Delivered";

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.HOUR, 8);
        Timestamp deliveryTime = new Timestamp(calendar.getTimeInMillis());

        boolean success = db.updateDeliveryStatus(deliveryID, newStatus, deliveryTime);
        if (!success) {
            request.setAttribute("errorMessage", "Failed to update the delivery status.");
        }
    }
}
