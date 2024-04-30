package ict.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ict.db.AsmDB;
import ict.bean.EquipmentBean;
import ict.bean.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Soman
 */
@WebServlet(name = "EquipmentController", urlPatterns = {"/EquipmentController"})
public class EquipmentController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();

        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EquipmentBean> equipmentList = db.fetchGroupedEquipment();
            request.setAttribute("equipmentList", equipmentList);

            String dashboardURL = getDashboardURL(request);
            request.setAttribute("dashboardURL", dashboardURL);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error fetching equipment data: " + e.getMessage());
        }

        request.getRequestDispatcher("/equipment_list.jsp").forward(request, response);
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
