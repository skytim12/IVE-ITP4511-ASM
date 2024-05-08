package ict.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import ict.db.AsmDB;
import ict.bean.EquipmentBean;
import ict.bean.UserBean;
import ict.bean.ItemBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

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
            HttpSession session = request.getSession();
            List<ItemBean> cart = (List<ItemBean>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }
            request.setAttribute("cart", cart);
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EquipmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("addToWishlist".equals(action)) {
            try {
                addToWishlist(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(EquipmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("addToCart".equals(action)) {
            addToCart(request, response);
        } else if ("confirmCart".equals(action)) {
            try {
                confirmCart(request);
                 request.setAttribute("successMessage", "Record add successfully.");
            } catch (SQLException ex) {
                Logger.getLogger(EquipmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("removeFromCart".equals(action)) {
            removeFromCart(request, response);
        } else {
            request.setAttribute("errorMessage", "Unknown action.");
        }

        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EquipmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        doGet(request, response);
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String campusName = request.getParameter("campus");
        String condition = request.getParameter("condition");

        HttpSession session = request.getSession();
        List<ItemBean> cart = (List<ItemBean>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(item -> item.getName().equals(name) && item.getCampusName().equals(campusName) && item.getCondition().equals(condition));
            session.setAttribute("cart", cart);
        }

    }

    private void addToWishlist(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String equipmentID = request.getParameter("equipmentID");
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        if (user != null && equipmentID != null && !equipmentID.isEmpty()) {
            boolean success = db.addWishlistItem(user.getUserID(), equipmentID);
            if (success) {
                request.setAttribute("successMessage", "Item added to wishlist successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to add item to wishlist.");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid user session or equipment data.");
        }

    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String campusName = request.getParameter("campus");
        String condition = request.getParameter("condition");
        String currentCampus = request.getParameter("currentCampus");
        int totalAvailable = Integer.parseInt(request.getParameter("total"));
        int quantityToAdd = Integer.parseInt(request.getParameter("quantity"));
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date reservedFrom = null;
        Date reservedTo = null;

        try {
            reservedFrom = java.sql.Date.valueOf(request.getParameter("reservedFrom"));
            reservedTo = java.sql.Date.valueOf(request.getParameter("reservedTo"));
            if (!reservedFrom.before(sdf.parse(sdf.format(today))) && !reservedTo.before(sdf.parse(sdf.format(today)))) {
                HttpSession session = request.getSession();
                List<ItemBean> cart = (List<ItemBean>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new ArrayList<>();
                }

                boolean itemExists = false;
                for (ItemBean item : cart) {
                    if (item.getName().equals(name) && item.getCampusName().equals(campusName) && item.getCondition().equals(condition)) {
                        if (currentCampus != null && item.getCurrentCampus() != null && !item.getCurrentCampus().equals(currentCampus)) {
                            continue;
                        }
                        if (item.getQuantity() + quantityToAdd > totalAvailable) {
                            request.setAttribute("errorMessage", "Cannot add more items than available in stock.");
                            return;
                        }
                        item.setQuantity(item.getQuantity() + quantityToAdd);
                        item.setReservedFrom(reservedFrom);
                        item.setReservedTo(reservedTo);
                        itemExists = true;
                        break;
                    }
                }

                if (!itemExists) {
                    if (quantityToAdd > totalAvailable) {
                        request.setAttribute("errorMessage", "Cannot add more items than available in stock.");
                        return;
                    }
                    ItemBean newItem = new ItemBean(name, campusName, condition, currentCampus, quantityToAdd, reservedFrom, reservedTo);
                    cart.add(newItem);
                }

                session.setAttribute("cart", cart);
            } else {
                request.setAttribute("errorMessage", "Reservation dates cannot be in the past.");
            }
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Invalid date format.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid date.");
        } catch (NullPointerException e) {
            request.setAttribute("errorMessage", "Unexpected error: Some required fields may be null.");
        }
    }

    private void confirmCart(HttpServletRequest request) throws IOException, SQLException {
        HttpSession session = request.getSession();
        List<ItemBean> cart = (List<ItemBean>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            Connection conn = null;
            try {
                conn = db.getConnection();
                conn.setAutoCommit(false);

                UserBean user = (UserBean) session.getAttribute("userBean");
                if (user == null) {
                    throw new SQLException("User session not found.");
                }
                String destinationCampus = user.getCampusName();

                Date reservedFrom = cart.get(0).getReservedFrom();
                Date reservedTo = cart.get(0).getReservedTo();
                int reservationID = db.addReservationAndGetId(user.getUserID(), reservedFrom, reservedTo, destinationCampus);
                if (reservationID <= 0) {
                    throw new SQLException("Failed to create a reservation record.");
                }

                for (ItemBean item : cart) {
                    List<String> equipmentIDs = db.fetchEquipmentIDsForReservation(item.getName(), item.getQuantity(), item.getCampusName());
                    for (String equipmentID : equipmentIDs) {

                        boolean equipmentReserved = db.addReservationEquipment(reservationID, equipmentID);
                        if (!equipmentReserved) {
                            throw new SQLException("Failed to link equipment to reservation.");
                        }

                        boolean availabilityUpdated = db.setEquipmentUnavailable(equipmentID);
                        if (!availabilityUpdated) {
                            throw new SQLException("Failed to update equipment availability.");
                        }

                    }

                }

                boolean borrowingRecordAdded = db.addBorrowingRecord(reservationID, new java.util.Date()); 
                if (!borrowingRecordAdded) {
                    throw new SQLException("Failed to add borrowing record.");
                }

                conn.commit();
                session.removeAttribute("cart");
                request.setAttribute("successMessage", "Transaction successful and cart confirmed.");
            } catch (SQLException ex) {
                if (conn != null) {
                    conn.rollback();
                }
                request.setAttribute("errorMessage", "Failed to process cart: " + ex.getMessage());
            } finally {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            }
        } else {
            request.setAttribute("errorMessage", "Cart is empty or not initialized.");
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        List<EquipmentBean> equipmentList = null;

        if (user != null && "Staff".equals(user.getRole())) {
            equipmentList = db.fetchGroupedEquipment();
        } else {
            equipmentList = db.fetchGroupedEquipmentExcludeForStaff();
        }
        request.setAttribute("equipmentList", equipmentList);

        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);
        request.setAttribute("todayDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        request.getRequestDispatcher("/equipment_list.jsp").forward(request, response);
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
