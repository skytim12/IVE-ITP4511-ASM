/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import ict.db.AsmDB;
import ict.bean.UserBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "WishlistController", urlPatterns = {"/WishlistController"})
public class WishlistController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        db = new AsmDB(getServletContext().getInitParameter("dbUrl"), getServletContext().getInitParameter("dbUser"), getServletContext().getInitParameter("dbPassword"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("removeFromWishlist".equals(action)) {
            removeFromWishlist(request);
        }
        processRequest(request, response);
    }

    private void removeFromWishlist(HttpServletRequest request) throws IOException {
        String equipmentID = request.getParameter("equipmentID");
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        if (user != null && equipmentID != null) {
            try {
                db.removeFromWishlist(user.getUserID(), equipmentID);
                request.setAttribute("successMessage", "Item successfully removed from wishlist.");
            } catch (SQLException ex) {
                Logger.getLogger(WishlistController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", "Failed to remove item from wishlist: " + ex.getMessage());
            }
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);
        if (user != null) {
            try {
                List<?> wishlist = db.fetchWishlist(user.getUserID());
                request.setAttribute("wishlist", wishlist);
                request.getRequestDispatcher("/wishlist.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(WishlistController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", "Error retrieving wishlist: " + ex.getMessage());
                request.getRequestDispatcher("/error_page.jsp").forward(request, response);
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
