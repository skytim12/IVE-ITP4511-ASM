package ict.servlet;

import ict.bean.EquipmentBean;
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

/**
 * Servlet to handle inventory operations.
 */
@WebServlet(name = "InventoryController", urlPatterns = {"/InventoryController"})
public class InventoryController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize database connection parameters from servlet context
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "update":
                {
                    try {
                        updateEquipment(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "delete":
                {
                    try {
                        deleteEquipment(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "add":
                {
                    try {
                        addEquipment(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                default:
                    request.setAttribute("errorMessage", "Invalid action.");
                    break;
            }
        }
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("userBean");
        EquipmentBean equipment = new EquipmentBean();

        equipment.setEquipmentID(request.getParameter("equipmentID"));
        equipment.setName(request.getParameter("name"));
        equipment.setDescription(request.getParameter("description"));
        equipment.setAvailable(request.getParameter("available"));
        equipment.setCampusName(user.getCampusName());
        equipment.setCurrentCampus(user.getCampusName());
        equipment.setCondition(request.getParameter("condition"));
        equipment.setExclusiveForStaff(request.getParameter("exclusiveForStaff"));

        try {
            if (db.addEquipment(equipment)) {
                request.setAttribute("successMessage", "Equipment added successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to add equipment.");
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        processRequest(request, response);
    }

    private void updateEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String equipmentID = request.getParameter("equipmentID");
        String description = request.getParameter("description");
        String available = request.getParameter("available"); // "Yes" or "No"
        String currentCampus = request.getParameter("currentCampus");
        String condition = request.getParameter("condition");
        String exclusiveForStaff = request.getParameter("exclusiveForStaff"); // "Yes" or "No"

        EquipmentBean equipment = new EquipmentBean();
        equipment.setEquipmentID(equipmentID);
        equipment.setDescription(description);
        equipment.setAvailable(available);
        equipment.setCurrentCampus(currentCampus);
        equipment.setCondition(condition);
        equipment.setExclusiveForStaff(exclusiveForStaff);

        try {
            boolean success = db.updateEquipment(equipment);
            if (success) {
                request.setAttribute("successMessage", "Equipment updated successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to update equipment.");
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
        }
        processRequest(request, response);
    }

    private void deleteEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String equipmentID = request.getParameter("equipmentID");

        try {
            boolean success = db.deleteEquipment(equipmentID);
            if (success) {
                request.setAttribute("successMessage", "Equipment deleted successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to delete equipment.");
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
        }
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        if (user == null) {
            request.setAttribute("errorMessage", "You must be logged in to access this page.");
            request.getRequestDispatcher("/login_page.jsp").forward(request, response);
            return;
        }

        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);
        
        String newEquipmentID = db.generateUniqueEquipmentID();
        request.setAttribute("newEquipmentID", newEquipmentID);

        try {
            List<EquipmentBean> equipmentList = db.fetchTechEquipmentList(user.getCampusName());
            request.setAttribute("equipmentList", equipmentList);
            request.getRequestDispatcher("/inventory_management.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error fetching equipment list: " + e.getMessage());
            request.getRequestDispatcher("/error_page.jsp").forward(request, response);
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
        return "";  // Fallback URL if no user session or unknown role
    }
}
