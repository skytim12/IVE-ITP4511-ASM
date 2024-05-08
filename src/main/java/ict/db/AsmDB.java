/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Soman
 */
public class AsmDB {

    private String url = "";
    private String username = "";
    private String password = "";

    public AsmDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }

    public void createTables() throws IOException {

        Statement stmnt = null;
        Connection cnnct = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();

            String[] createTableQueries = {
                // Create Campus table
                "CREATE TABLE IF NOT EXISTS Campus ("
                + "CampusName ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi') PRIMARY KEY, "
                + "Address TEXT NOT NULL"
                + ")",
                // Create Users table
                "CREATE TABLE IF NOT EXISTS Users ("
                + "UserID VARCHAR(20) PRIMARY KEY, "
                + "Username VARCHAR(255) UNIQUE NOT NULL, "
                + "Password VARCHAR(255) NOT NULL, "
                + "Role ENUM('GeneralUser', 'Technician', 'Courier', 'Staff', 'AdminTechnician') NOT NULL, "
                + "FullName VARCHAR(255), "
                + "CampusName ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "FOREIGN KEY (CampusName) REFERENCES Campus(CampusName)"
                + ")",
                // Create Equipment table
                "CREATE TABLE IF NOT EXISTS Equipment ("
                + "EquipmentID VARCHAR(10) PRIMARY KEY, "
                + "Name VARCHAR(255) NOT NULL, "
                + "Description TEXT, "
                + "Available ENUM('Yes', 'No') DEFAULT 'Yes', "
                + "CampusName ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "CurrentCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi') NOT NULL, "
                + "EquipmentCondition ENUM('New', 'Good', 'Fair', 'Poor', 'Out of Service') NOT NULL DEFAULT 'Good', "
                + "ExclusiveForStaff ENUM('Yes', 'No') DEFAULT 'No', "
                + "FOREIGN KEY (CampusName) REFERENCES Campus(CampusName), "
                + "FOREIGN KEY (CurrentCampus) REFERENCES Campus(CampusName)"
                + ")",
                // Create Reservation table
                "CREATE TABLE IF NOT EXISTS Reservation ("
                + "ReservationID INT AUTO_INCREMENT PRIMARY KEY, "
                + "UserID VARCHAR(20), "
                + "ReservedFrom DATE, "
                + "ReservedTo DATE, "
                + "DestinationCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID), "
                + "FOREIGN KEY (DestinationCampus) REFERENCES Campus(CampusName)"
                + ")",
                // Create ReservationEquipment table
                "CREATE TABLE IF NOT EXISTS ReservationEquipment ("
                + "ReservationID INT, "
                + "EquipmentID VARCHAR(10), "
                + "ReturnDate DATE, "
                + "Status ENUM('Waiting','Reserved', 'Borrowed', 'Returned', 'Success', 'Error', 'Declined') DEFAULT 'Waiting',"
                + "PRIMARY KEY (ReservationID, EquipmentID), "
                + "FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID)"
                + ")",
                // Create BorrowingRecords table
                "CREATE TABLE IF NOT EXISTS BorrowingRecords ("
                + "RecordID INT AUTO_INCREMENT PRIMARY KEY, "
                + "ReservationID INT, "
                + "BorrowDate DATE, "
                + "FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID)"
                + ")",
                // Create Wishlist table
                "CREATE TABLE IF NOT EXISTS Wishlist ("
                + "WishlistID INT AUTO_INCREMENT PRIMARY KEY, "
                + "UserID VARCHAR(20), "
                + "EquipmentID VARCHAR(10), "
                + "DateAdded DATE, "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
                + "CONSTRAINT UC_UserEquipment UNIQUE (UserID, EquipmentID)"
                + ")",
                // Create Delivery table
                "CREATE TABLE IF NOT EXISTS Delivery ("
                + "DeliveryID INT AUTO_INCREMENT PRIMARY KEY, "
                + "ReservationID INT, "
                + "FromCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "ToCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "CourierID VARCHAR(20), "
                + "PickupTime DATETIME, "
                + "DeliveryTime DATETIME, "
                + "Status ENUM('Scheduled', 'In Transit', 'Delivered', 'Success'), "
                + "FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID), "
                + "FOREIGN KEY (FromCampus) REFERENCES Campus(CampusName), "
                + "FOREIGN KEY (ToCampus) REFERENCES Campus(CampusName), "
                + "FOREIGN KEY (CourierID) REFERENCES Users(UserID)"
                + ")",
                // Create DamageReports table
                "CREATE TABLE IF NOT EXISTS DamageReports ("
                + "ReportID INT AUTO_INCREMENT PRIMARY KEY, "
                + "EquipmentID VARCHAR(10), "
                + "ReportedBy VARCHAR(20), "
                + "ReportDate DATETIME, "
                + "Description TEXT, "
                + "Status ENUM('Reported', 'Confirmed'), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
                + "FOREIGN KEY (ReportedBy) REFERENCES Users(UserID)"
                + ")",
                // Create Notifications table
                "CREATE TABLE IF NOT EXISTS Notifications ("
                + "NotificationID INT AUTO_INCREMENT PRIMARY KEY, "
                + "UserID VARCHAR(20), "
                + "Message TEXT, "
                + "ReadStatus ENUM('Read', 'Unread') DEFAULT 'Unread', "
                + "NotificationDate DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID)"
                + ")"
            };

            for (String query : createTableQueries) {
                System.out.println(query);
                stmnt.executeUpdate(query);
            }

        } catch (SQLException ex) {
            System.out.println("SQL Error Message: " + ex.getMessage());
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public UserBean authenticateUser(String username, String password) throws SQLException, IOException {
        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = SHA2(?, 256)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UserBean user = new UserBean();
                user.setUserID(rs.getString("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setFullName(rs.getString("FullName"));
                user.setCampusName(rs.getString("CampusName"));
                user.setRole(rs.getString("Role"));
                return user;
            }
        }
        return null;
    }

    public List<UserBean> getAllUsersByCampus(String campusName) throws SQLException, IOException {
        List<UserBean> users = new ArrayList<>();
        String sql = "SELECT UserID, Username, FullName, Role, CampusName FROM Users WHERE CampusName = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, campusName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UserBean user = new UserBean();
                    user.setUserID(rs.getString("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setFullName(rs.getString("FullName"));
                    user.setRole(rs.getString("Role"));
                    user.setCampusName(rs.getString("CampusName"));
                    users.add(user);
                }
            }
        }
        return users;
    }

    public boolean addUser(UserBean user) throws SQLException, IOException {
        String query = "INSERT INTO Users (UserID, Username, Password, Role, FullName, CampusName) VALUES (?, ?, ?, ?, ?, ?)";
        Connection cnnct = null;
        PreparedStatement pstmt = null;
        try {
            cnnct = getConnection();
            pstmt = cnnct.prepareStatement(query);

            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getFullName());
            pstmt.setString(6, user.getCampusName());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
            if (cnnct != null) {
                cnnct.close();
            }
        }
    }

    public boolean updateUserDetails(UserBean user) throws SQLException, IOException {
        String sql = "UPDATE Users SET FullName = ?, Role = ?, CampusName = ? WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, user.getCampusName());
            pstmt.setString(4, user.getUserID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SQLException("Error updating user: " + e.getMessage(), e);
        }
    }

    public boolean deleteUser(String userID) throws SQLException, IOException {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting user: " + e.getMessage(), e);
        }
    }

    public boolean changeUserPassword(String userID, String hashedPassword) throws SQLException, IOException {
        String sql = "UPDATE Users SET Password = ? WHERE UserID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, userID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SQLException("Error changing password: " + e.getMessage(), e);
        }
    }

    public boolean addCampus(String campusName, String address) throws SQLException, IOException {
        String query = "INSERT INTO Campus (CampusName, Address) VALUES (?, ?)";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query)) {
            pstmt.setString(1, campusName);
            pstmt.setString(2, address);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean addEquipment(EquipmentBean equipment) throws SQLException, IOException {
        String query = "INSERT INTO Equipment (EquipmentID, Name, Description, Available, CampusName, CurrentCampus, EquipmentCondition, ExclusiveForStaff) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection cnnct = null;
        PreparedStatement pstmt = null;
        try {
            cnnct = getConnection();
            pstmt = cnnct.prepareStatement(query);

            pstmt.setString(1, equipment.getEquipmentID());
            pstmt.setString(2, equipment.getName());
            pstmt.setString(3, equipment.getDescription());
            pstmt.setString(4, equipment.getAvailable());
            pstmt.setString(5, equipment.getCampusName());
            pstmt.setString(6, equipment.getCurrentCampus()); // Handle current campus
            pstmt.setString(7, equipment.getCondition());
            pstmt.setString(8, equipment.getExclusiveForStaff());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (cnnct != null) {
                cnnct.close();
            }
        }
    }

    public List<EquipmentBean> fetchEquipmentList() throws SQLException, IOException {
        List<EquipmentBean> equipmentList = new ArrayList<>();
        String query = "SELECT EquipmentID, Name, Description, Available, CampusName, CurrentCampus, EquipmentCondition FROM Equipment";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentID(rs.getString("EquipmentID"));
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
                equipment.setCurrentCampus(rs.getString("CurrentCampus")); // Setting current campus
                equipment.setCondition(rs.getString("EquipmentCondition"));
                equipmentList.add(equipment);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return equipmentList;
    }

    public List<EquipmentBean> fetchGroupedEquipment() throws SQLException, IOException {
        List<EquipmentBean> groupedEquipmentList = new ArrayList<>();
        String query = "SELECT GROUP_CONCAT(EquipmentID) AS EquipmentIDs, Name, Description, Available, CampusName, CurrentCampus, EquipmentCondition, ExclusiveForStaff, COUNT(*) AS TotalQuantity "
                + "FROM Equipment "
                + "WHERE EquipmentCondition != 'Out of Service' "
                + "GROUP BY Name, Available, CampusName, CurrentCampus, EquipmentCondition, ExclusiveForStaff "
                + "ORDER BY Available ASC, ExclusiveForStaff ASC, "
                + "         CASE "
                + "             WHEN EquipmentCondition = 'New' THEN 1 "
                + "             WHEN EquipmentCondition = 'Good' THEN 2 "
                + "             WHEN EquipmentCondition = 'Fair' THEN 3 "
                + "             WHEN EquipmentCondition = 'Poor' THEN 4 "
                + "         END";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentIDs(rs.getString("EquipmentIDs")); // Store concatenated IDs
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
                equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                equipment.setCondition(rs.getString("EquipmentCondition"));
                equipment.setTotalQuantity(rs.getInt("TotalQuantity"));
                equipment.setExclusiveForStaff(rs.getString("ExclusiveForStaff"));
                groupedEquipmentList.add(equipment);
            }
        }
        return groupedEquipmentList;
    }

    public List<EquipmentBean> fetchGroupedEquipmentExcludeForStaff() throws SQLException, IOException {
        List<EquipmentBean> equipmentList = new ArrayList<>();
        String query = "SELECT GROUP_CONCAT(EquipmentID) AS EquipmentIDs, Name, Description, Available, CampusName,CurrentCampus, EquipmentCondition,ExclusiveForStaff, COUNT(*) AS TotalQuantity "
                + "FROM Equipment "
                + "WHERE EquipmentCondition != 'Out of Service' AND ExclusiveForStaff = 'No' "
                + "GROUP BY Name, Available, CampusName, EquipmentCondition "
                + "ORDER BY Available ASC, "
                + "         CASE "
                + "             WHEN EquipmentCondition = 'New' THEN 1 "
                + "             WHEN EquipmentCondition = 'Good' THEN 2 "
                + "             WHEN EquipmentCondition = 'Fair' THEN 3 "
                + "             WHEN EquipmentCondition = 'Poor' THEN 4 "
                + "         END";

        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentIDs(rs.getString("EquipmentIDs"));
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
                equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                equipment.setCondition(rs.getString("EquipmentCondition"));
                equipment.setTotalQuantity(rs.getInt("TotalQuantity"));
                equipment.setExclusiveForStaff(rs.getString("ExclusiveForStaff"));
                equipmentList.add(equipment);
            }
        }
        return equipmentList;
    }

    public String generateUniqueEquipmentID() throws SQLException, IOException {
        String equipmentID = null;
        try (Connection cnnct = getConnection(); Statement stmnt = cnnct.createStatement(); ResultSet rs = stmnt.executeQuery("SELECT EquipmentID FROM Equipment ORDER BY EquipmentID DESC LIMIT 1")) {

            if (rs.next()) {
                String lastEquipmentID = rs.getString("EquipmentID");
                int lastNumber = Integer.parseInt(lastEquipmentID.substring(2));
                int nextNumber = lastNumber + 1;
                equipmentID = "EQ" + String.format("%03d", nextNumber);
            } else {
                equipmentID = "EQ001";
            }
        }
        return equipmentID;
    }

    public List<EquipmentBean> UserfetchReservableEquipmentList() throws SQLException, IOException {
        List<EquipmentBean> reservableEquipmentList = new ArrayList<>();
        String query = "SELECT Name, Description, Available, CampusName, EquipmentCondition, CurrentCampus, COUNT(*) AS TotalQuantity "
                + "FROM Equipment "
                + "WHERE Available = 'Yes' AND EquipmentCondition != 'Out of Service' AND ExclusiveForStaff = 'No' "
                + "GROUP BY Name, Description, Available, CampusName, EquipmentCondition, CurrentCampus "
                + "ORDER BY Name, Available, CampusName";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
                equipment.setCondition(rs.getString("EquipmentCondition"));
                equipment.setCurrentCampus(rs.getString("CurrentCampus"));  // Set the current campus
                equipment.setTotalQuantity(rs.getInt("TotalQuantity"));
                reservableEquipmentList.add(equipment);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return reservableEquipmentList;
    }

    public List<EquipmentBean> StafffetchReservableEquipmentList() throws SQLException, IOException {
        List<EquipmentBean> reservableEquipmentList = new ArrayList<>();
        String query = "SELECT Name, Description, Available, CampusName, EquipmentCondition, CurrentCampus, COUNT(*) AS TotalQuantity "
                + "FROM Equipment "
                + "WHERE Available = 'Yes' AND EquipmentCondition != 'Out of Service'"
                + "GROUP BY Name, Description, Available, CampusName, EquipmentCondition, CurrentCampus "
                + "ORDER BY Name, Available, CampusName";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
                equipment.setCondition(rs.getString("EquipmentCondition"));
                equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                equipment.setTotalQuantity(rs.getInt("TotalQuantity"));
                reservableEquipmentList.add(equipment);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return reservableEquipmentList;
    }

    public List<EquipmentBean> fetchTechEquipmentList(String userCampusName) throws SQLException, IOException {
        List<EquipmentBean> equipmentList = new ArrayList<>();
        String query = "SELECT EquipmentID, Name, Description, Available, CampusName, CurrentCampus, EquipmentCondition, ExclusiveForStaff "
                + "FROM Equipment "
                + "WHERE (CampusName = ? OR CurrentCampus = ?) "
                + "ORDER BY CASE WHEN EquipmentCondition = 'Out of Service' THEN 1 ELSE 0 END, "
                + "Available ASC, "
                + "Name";

        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query)) {

            pstmt.setString(1, userCampusName);
            pstmt.setString(2, userCampusName);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EquipmentBean equipment = new EquipmentBean();
                    equipment.setEquipmentID(rs.getString("EquipmentID"));
                    equipment.setName(rs.getString("Name"));
                    equipment.setDescription(rs.getString("Description"));
                    equipment.setAvailable(rs.getString("Available"));
                    equipment.setCampusName(rs.getString("CampusName"));
                    equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                    equipment.setCondition(rs.getString("EquipmentCondition"));
                    equipment.setExclusiveForStaff(rs.getString("ExclusiveForStaff"));
                    equipmentList.add(equipment);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return equipmentList;
    }

    public boolean addWishlistItem(String userID, String equipmentID) throws SQLException, IOException {
        String sql = "INSERT INTO Wishlist (UserID, EquipmentID, DateAdded) VALUES (?, ?, ?)";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

            pstmt.setString(1, userID);
            pstmt.setString(2, equipmentID);
            pstmt.setDate(3, new java.sql.Date(new Date().getTime()));

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return false;
        }
    }

    public int addReservationAndGetId(String userID, Date reservedFrom, Date reservedTo, String destinationCampus) throws SQLException, IOException {
        String sql = "INSERT INTO Reservation (UserID, ReservedFrom, ReservedTo, DestinationCampus) VALUES (?, ?, ?, ?)";
        int reservationId = 0;
        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, userID);
            pstmt.setDate(2, new java.sql.Date(reservedFrom.getTime()));
            pstmt.setDate(3, new java.sql.Date(reservedTo.getTime()));
            pstmt.setString(4, destinationCampus);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        reservationId = rs.getInt(1);
                    }
                }
                sendNotificationsToTechAndAdminByCampus(destinationCampus, "New reservation needs approval", "A new reservation with ID " + reservationId + " needs your attention.");
            }
            return reservationId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<String> fetchEquipmentIDsForReservation(String equipmentType, int quantity, String campusName) throws SQLException, IOException {
        List<String> equipmentIDs = new ArrayList<>();
        String query = "SELECT EquipmentID FROM Equipment "
                + "WHERE Name = ? AND Available = 'Yes' AND CampusName = ? "
                + "ORDER BY EquipmentID ASC LIMIT ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, equipmentType);
            pstmt.setString(2, campusName);
            pstmt.setInt(3, quantity);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    equipmentIDs.add(rs.getString("EquipmentID"));
                }
            }
        }

        return equipmentIDs;
    }

    public boolean addReservationEquipment(int reservationID, String equipmentID) throws SQLException, IOException {
        String query = "INSERT INTO ReservationEquipment (ReservationID, EquipmentID) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reservationID);
            pstmt.setString(2, equipmentID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean addBorrowingRecord(int reservationID, Date borrowDate) throws SQLException, IOException {
        String sql = "INSERT INTO BorrowingRecords (ReservationID, BorrowDate) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            pstmt.setDate(2, new java.sql.Date(borrowDate.getTime()));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean setEquipmentUnavailable(String equipmentID) throws SQLException, IOException {
        String query = "UPDATE Equipment SET Available = 'No' WHERE EquipmentID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, equipmentID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<BorrowingRecordsBean> fetchBorrowingRecords(String userID) throws SQLException, IOException {
        List<BorrowingRecordsBean> records = new ArrayList<>();
        String sql = "SELECT br.RecordID, br.BorrowDate, re.ReturnDate, r.ReservationID, "
                + "GROUP_CONCAT(DISTINCT e.Name SEPARATOR '&') AS EquipmentNames, COUNT(re.EquipmentID) AS TotalQuantity "
                + "FROM BorrowingRecords br "
                + "JOIN Reservation r ON br.ReservationID = r.ReservationID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "LEFT JOIN Delivery d ON r.ReservationID = d.ReservationID "
                + "WHERE r.UserID = ? "
                + "GROUP BY br.RecordID, br.BorrowDate, re.ReturnDate, r.ReservationID "
                + "ORDER BY r.ReservationID DESC";

        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BorrowingRecordsBean record = new BorrowingRecordsBean();
                record.setRecordID(rs.getInt("RecordID"));
                record.setBorrowDate(rs.getDate("BorrowDate"));
                record.setReturnDate(rs.getDate("ReturnDate"));
                record.setEquipmentNames(rs.getString("EquipmentNames"));
                record.setTotalQuantity(rs.getInt("TotalQuantity"));
                record.setReservationID(rs.getInt("ReservationID"));
                record.setEquipmentList(fetchEquipmentListForReservation(rs.getInt("ReservationID"), conn)); // Fetching the equipment list
                records.add(record);
            }
        }
        return records;
    }

    public void returnEquipment(HttpServletRequest request) throws SQLException, IOException {

        String equipmentID = (request.getParameter("equipmentID"));
        int reservationID = Integer.parseInt(request.getParameter("reservationID"));
        Connection conn = null;

        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);

            String updateBorrowingRecordsSql = "UPDATE ReservationEquipment SET ReturnDate = CURRENT_DATE(), Status = 'Returned' "
                    + "WHERE ReservationID = ? AND EquipmentID = ? AND ReturnDate IS NULL";
            try (PreparedStatement pstmt = conn.prepareStatement(updateBorrowingRecordsSql)) {
                pstmt.setInt(1, reservationID);
                pstmt.setString(2, equipmentID);
                int updatedRows = pstmt.executeUpdate();
                if (updatedRows == 0) {
                    throw new SQLException("No records updated, check if the record ID is correct and the equipment hasn't been returned yet.");
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<EquipmentBean> fetchWishlist(String userID) throws SQLException, IOException {
        List<EquipmentBean> wishlist = new ArrayList<>();
        String query = "SELECT e.EquipmentID, e.Name, e.Description "
                + "FROM Wishlist w JOIN Equipment e ON w.EquipmentID = e.EquipmentID "
                + "WHERE w.UserID = ?";
        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EquipmentBean equipment = new EquipmentBean();
                    equipment.setEquipmentID(rs.getString("EquipmentID"));
                    equipment.setName(rs.getString("Name"));
                    equipment.setDescription(rs.getString("Description"));
                    wishlist.add(equipment);
                }
            }
        }
        return wishlist;
    }

    public boolean removeFromWishlist(String userID, String equipmentID) throws SQLException, IOException {
        String query = "DELETE FROM Wishlist WHERE UserID = ? AND EquipmentID = ?";
        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, equipmentID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public void updateUserProfile(String userID, String fullName, String newPassword) throws SQLException, IOException {
        if ("".equals(newPassword)) {
            String sql = "UPDATE Users SET FullName = ? WHERE UserID = ?";
            try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, fullName);
                pstmt.setString(2, userID);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating user failed, no rows affected.");
                }
            } catch (SQLException e) {
                throw e;
            }
        } else {
            String sql = "UPDATE Users SET FullName = ?, Password = ? WHERE UserID = ?";
            try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, fullName);
                pstmt.setString(2, hashPassword(newPassword));
                pstmt.setString(3, userID);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating user failed, no rows affected.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public boolean updateEquipment(EquipmentBean equipment) throws SQLException, IOException {
        String sql = "UPDATE Equipment SET "
                + "Description = ?, "
                + "Available = ?, "
                + "CurrentCampus = ?, "
                + "EquipmentCondition = ?, "
                + "ExclusiveForStaff = ? "
                + "WHERE EquipmentID = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getDescription());
            pstmt.setString(2, equipment.getAvailable());
            pstmt.setString(3, equipment.getCurrentCampus());
            pstmt.setString(4, equipment.getCondition());
            pstmt.setString(5, equipment.getExclusiveForStaff());
            pstmt.setString(6, equipment.getEquipmentID());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0 && "Yes".equals(equipment.getAvailable())) {
                notifyUsersOnWishlist(equipment.getEquipmentID());
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw e;
        }
    }

    public boolean deleteEquipment(String equipmentID) throws SQLException, IOException {
        boolean success = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String sql = "DELETE FROM equipment WHERE equipmentID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, equipmentID);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
            }
        } finally {
            // Close resources
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return success;
    }

    public List<ReservationBean> fetchAllReservations(String userCampus) throws SQLException, IOException {
        List<ReservationBean> reservations = new ArrayList<>();
        String sql = "SELECT r.ReservationID, r.UserID, u.FullName AS userName, r.ReservedFrom, r.ReservedTo, r.Status "
                + "FROM Reservation r "
                + "JOIN Users u ON r.UserID = u.UserID "
                + "WHERE EXISTS ("
                + "    SELECT 1 FROM ReservationEquipment re "
                + "    JOIN Equipment e ON re.EquipmentID = e.EquipmentID AND e.CampusName = ? "
                + "    WHERE re.ReservationID = r.ReservationID"
                + ") "
                + "ORDER BY r.ReservedFrom DESC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ReservationBean reservation = new ReservationBean();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setUserID(rs.getString("UserID"));
                    reservation.setUserName(rs.getString("userName"));
                    reservation.setReservedFrom(rs.getDate("ReservedFrom"));
                    reservation.setReservedTo(rs.getDate("ReservedTo"));
                    reservation.setStatus(rs.getString("Status"));
                    reservation.setEquipmentList(fetchEquipmentListForReservation(rs.getInt("ReservationID"), conn));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public List<ReservationBean> fetchWaitingReservations(String userCampus) throws SQLException, IOException {
        List<ReservationBean> reservations = new ArrayList<>();
        String sql = "SELECT DISTINCT r.ReservationID, r.UserID, u.FullName AS userName, r.ReservedFrom, r.ReservedTo, re.Status "
                + "FROM Reservation r "
                + "JOIN Users u ON r.UserID = u.UserID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "JOIN BorrowingRecords br ON br.ReservationID = r.ReservationID "
                + "WHERE e.CampusName = ? AND re.Status = 'Waiting' "
                + "ORDER BY r.ReservedFrom DESC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ReservationBean reservation = new ReservationBean();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setUserID(rs.getString("UserID"));
                    reservation.setUserName(rs.getString("userName"));
                    reservation.setReservedFrom(rs.getDate("ReservedFrom"));
                    reservation.setReservedTo(rs.getDate("ReservedTo"));
                    reservation.setStatus(rs.getString("Status"));
                    reservation.setEquipmentList(fetchEquipmentListForReservationAndCampus(rs.getInt("ReservationID"), userCampus, conn));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public List<ReservationBean> fetchReservedReservations(String userCampus) throws SQLException, IOException {
        List<ReservationBean> reservations = new ArrayList<>();
        String sql = "SELECT DISTINCT r.ReservationID, r.UserID, u.FullName AS userName, r.ReservedFrom, r.ReservedTo, re.Status "
                + "FROM Reservation r "
                + "JOIN Users u ON r.UserID = u.UserID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "JOIN BorrowingRecords br ON br.ReservationID = r.ReservationID "
                + "WHERE e.CampusName = ?"
                + "ORDER BY r.ReservationID DESC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ReservationBean reservation = new ReservationBean();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setUserID(rs.getString("UserID"));
                    reservation.setUserName(rs.getString("userName"));
                    reservation.setReservedFrom(rs.getDate("ReservedFrom"));
                    reservation.setReservedTo(rs.getDate("ReservedTo"));
                    reservation.setStatus(rs.getString("Status"));
                    reservation.setEquipmentList(fetchEquipmentListForReservationAndCampus(rs.getInt("ReservationID"), userCampus, conn));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public List<ReservationBean> fetchReservationsForDelivery(String userCampus) throws SQLException, IOException {
        List<ReservationBean> reservations = new ArrayList<>();
        String sql = "SELECT r.ReservationID, r.UserID,r.DestinationCampus,c.Address AS CampusAddress, u.FullName AS userName, r.ReservedFrom, r.ReservedTo  "
                + "FROM Reservation r "
                + "JOIN Users u ON r.UserID = u.UserID "
                + "JOIN BorrowingRecords br ON r.ReservationID = br.ReservationID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "LEFT JOIN Delivery d ON r.ReservationID = d.ReservationID "
                + "JOIN Campus c ON r.DestinationCampus = c.CampusName "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "WHERE e.CampusName = ? AND re.Status IN ('Reserved', 'Returned')"
                + "ORDER BY r.ReservedFrom DESC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ReservationBean reservation = new ReservationBean();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setUserID(rs.getString("UserID"));
                    reservation.setUserName(rs.getString("userName"));
                    reservation.setReservedFrom(rs.getDate("ReservedFrom"));
                    reservation.setReservedTo(rs.getDate("ReservedTo"));
                    reservation.setToCampus(rs.getString("DestinationCampus"));
                    reservation.setAddress(rs.getString("CampusAddress"));
                    reservation.setEquipmentList(fetchEquipmentListForReservationAndCampus(rs.getInt("ReservationID"), userCampus, conn));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public ReservationBean getReservationDetails(String reservationID) throws SQLException, IOException {
        ReservationBean reservation = null;
        String sql = "SELECT r.ReservationID, r.UserID, u.FullName AS userName, r.ReservedFrom, r.ReservedTo, r.Status "
                + "FROM Reservation r "
                + "JOIN Users u ON r.UserID = u.UserID "
                + "WHERE r.ReservationID = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(reservationID));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    reservation = new ReservationBean();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setUserID(rs.getString("UserID"));
                    reservation.setUserName(rs.getString("userName"));
                    reservation.setReservedFrom(rs.getDate("ReservedFrom"));
                    reservation.setReservedTo(rs.getDate("ReservedTo"));
                    reservation.setStatus(rs.getString("Status"));
                    reservation.setEquipmentList(fetchEquipmentListForReservation(rs.getInt("ReservationID"), conn));
                }
            }
        }
        return reservation;
    }

    private List<EquipmentBean> fetchEquipmentListForReservation(int reservationID, Connection conn) throws SQLException {
        List<EquipmentBean> equipmentList = new ArrayList<>();
        String sql = "SELECT e.EquipmentID, e.Name, e.Description, e.CampusName, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff, re.Status "
                + "FROM Equipment e "
                + "JOIN ReservationEquipment re ON e.EquipmentID = re.EquipmentID "
                + "WHERE re.ReservationID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EquipmentBean equipment = new EquipmentBean();
                    equipment.setEquipmentID(rs.getString("EquipmentID"));
                    equipment.setName(rs.getString("Name"));
                    equipment.setDescription(rs.getString("Description"));
                    equipment.setCampusName(rs.getString("CampusName"));
                    equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                    equipment.setCondition(rs.getString("EquipmentCondition"));
                    equipment.setExclusiveForStaff(rs.getString("ExclusiveForStaff"));
                    equipment.setStatus(rs.getString("Status"));
                    equipmentList.add(equipment);
                }
            }
        }
        return equipmentList;
    }

    private List<EquipmentBean> fetchEquipmentListForReservationAndCampus(int reservationID, String userCampus, Connection conn) throws SQLException {
        List<EquipmentBean> equipmentList = new ArrayList<>();
        String sql = "SELECT e.EquipmentID, e.Name, e.Description, e.CampusName, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff, re.Status "
                + "FROM Equipment e "
                + "JOIN ReservationEquipment re ON e.EquipmentID = re.EquipmentID "
                + "WHERE re.ReservationID = ? AND e.CampusName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            pstmt.setString(2, userCampus);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EquipmentBean equipment = new EquipmentBean();
                    equipment.setEquipmentID(rs.getString("EquipmentID"));
                    equipment.setName(rs.getString("Name"));
                    equipment.setDescription(rs.getString("Description"));
                    equipment.setCampusName(rs.getString("CampusName"));
                    equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                    equipment.setCondition(rs.getString("EquipmentCondition"));
                    equipment.setExclusiveForStaff(rs.getString("ExclusiveForStaff"));
                    equipment.setStatus(rs.getString("Status"));
                    equipmentList.add(equipment);
                }
            }
        }
        return equipmentList;
    }

    private List<EquipmentBean> fetchEquipmentListForReservationDelivery(int reservationID, Connection conn) throws SQLException {
        List<EquipmentBean> equipmentList = new ArrayList<>();
        String sql = "SELECT e.EquipmentID, e.Name, e.Description, e.CampusName, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff, re.Status "
                + "FROM Equipment e "
                + "JOIN ReservationEquipment re ON e.EquipmentID = re.EquipmentID "
                + "WHERE re.ReservationID = ? AND re.Status IN ('Reserved', 'Returned')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EquipmentBean equipment = new EquipmentBean();
                    equipment.setEquipmentID(rs.getString("EquipmentID"));
                    equipment.setName(rs.getString("Name"));
                    equipment.setDescription(rs.getString("Description"));
                    equipment.setCampusName(rs.getString("CampusName"));
                    equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                    equipment.setCondition(rs.getString("EquipmentCondition"));
                    equipment.setExclusiveForStaff(rs.getString("ExclusiveForStaff"));
                    equipment.setStatus(rs.getString("Status"));
                    equipmentList.add(equipment);
                }
            }
        }
        return equipmentList;
    }

    private List<EquipmentBean> fetchEquipmentListForReservationDeliveryCampus(int reservationID, String campusName, Connection conn) throws SQLException {
        List<EquipmentBean> equipmentList = new ArrayList<>();
        String sql = "SELECT e.EquipmentID, e.Name, e.Description, e.CampusName, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff, re.Status "
                + "FROM Equipment e "
                + "JOIN ReservationEquipment re ON e.EquipmentID = re.EquipmentID "
                + "WHERE re.ReservationID = ? AND re.Status IN ('Reserved', 'Returned') AND e.CampusName = ? ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            pstmt.setString(2, campusName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EquipmentBean equipment = new EquipmentBean();
                    equipment.setEquipmentID(rs.getString("EquipmentID"));
                    equipment.setName(rs.getString("Name"));
                    equipment.setDescription(rs.getString("Description"));
                    equipment.setCampusName(rs.getString("CampusName"));
                    equipment.setCurrentCampus(rs.getString("CurrentCampus"));
                    equipment.setCondition(rs.getString("EquipmentCondition"));
                    equipment.setExclusiveForStaff(rs.getString("ExclusiveForStaff"));
                    equipment.setStatus(rs.getString("Status"));
                    equipmentList.add(equipment);
                }
            }
        }
        return equipmentList;
    }

    public void updateReservationEquipmentRecordStatus(String reservationID, String equipmentID, String status) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement pstmtUser = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sqlFetchUser = "SELECT UserID FROM Reservation WHERE ReservationID = ?";
            pstmtUser = conn.prepareStatement(sqlFetchUser);
            pstmtUser.setString(1, reservationID);
            rs = pstmtUser.executeQuery();
            String userID = null;
            if (rs.next()) {
                userID = rs.getString("UserID");
            }

            if (userID == null) {
                throw new SQLException("No user found with the given reservation ID.");
            }

            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE ReservationEquipment SET Status = ? WHERE ReservationID = ? AND EquipmentID = ?")) {
                pstmt.setString(1, status);
                pstmt.setString(2, reservationID);
                pstmt.setString(3, equipmentID);
                pstmt.executeUpdate();
            }

            if ("Reserved".equals(status)) {
                sendNotification(userID, "Reservation Approved!", "Your reservation for " + getEquipmentName(equipmentID) + " has been approved.");
            }

            if ("Decline".equals(status)) {
                sendNotification(userID, "Reservation Declined!", "Your reservation for " + getEquipmentName(equipmentID) + " has been declined.");
                try (PreparedStatement pstmt = conn.prepareStatement("UPDATE Equipment SET Available = 'Yes' WHERE EquipmentID = ?")) {
                    pstmt.setString(1, equipmentID);
                    pstmt.executeUpdate();
                }

                notifyUsersOnWishlist(equipmentID);

            }

            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public String getEquipmentName(String equipmentID) throws SQLException, IOException {
        String equipmentName = null;
        String sql = "SELECT Name FROM Equipment WHERE EquipmentID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                equipmentName = rs.getString("Name");
            }
        } catch (SQLException e) {
            throw new SQLException("Error while fetching equipment name: " + e.getMessage(), e);
        }
        return equipmentName;  // Return the fetched name or null
    }

    public boolean addDelivery(int reservationID, String fromCampus, String toCampus, String status) throws SQLException, IOException {
        String sql = "INSERT INTO Delivery (ReservationID, FromCampus, ToCampus, Status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            pstmt.setString(2, fromCampus);
            pstmt.setString(3, toCampus);
            pstmt.setString(4, status);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SQLException("Error adding delivery: " + e.getMessage(), e);
        }
    }

    public List<DeliveryBean> fetchAllDeliveries(String campusName) throws SQLException, IOException {
        List<DeliveryBean> deliveries = new ArrayList<>();

        String sql = "SELECT d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus, d.CourierID, d.PickupTime, d.DeliveryTime, d.Status, u.FullName AS CourierName "
                + "FROM Delivery d "
                + "JOIN Users u ON d.CourierID = u.UserID "
                + "WHERE d.ToCampus = ? AND d.Status = 'delivered' "
                + "ORDER BY d.PickupTime DESC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, campusName);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DeliveryBean delivery = new DeliveryBean();
                    delivery.setDeliveryID(rs.getInt("DeliveryID"));
                    delivery.setReservationID(rs.getInt("ReservationID"));
                    delivery.setFromCampus(rs.getString("FromCampus"));
                    delivery.setToCampus(rs.getString("ToCampus"));
                    delivery.setCourierID(rs.getString("CourierID"));
                    delivery.setUserName(rs.getString("CourierName"));
                    delivery.setPickupTime(rs.getTimestamp("PickupTime"));
                    delivery.setDeliveryTime(rs.getTimestamp("DeliveryTime"));
                    delivery.setStatus(rs.getString("Status"));
                    delivery.setEquipmentList(new ArrayList<>());
                    delivery.setEquipmentList(fetchEquipmentListForReservationDeliveryCampus(rs.getInt("ReservationID"), campusName, conn));
                    deliveries.add(delivery);

                }
            }
        }
        return deliveries;
    }

    public boolean checkInDelivery(int deliveryID, int reservationID, String userCampus) throws SQLException, IOException {
        boolean success = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtCheckAvailability = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            List<EquipmentBean> equipmentList = fetchEquipmentListForReservationDelivery(reservationID, conn);

            for (EquipmentBean equipment : equipmentList) {
                String sqlUpdate;
                if ("Reserved".equals(equipment.getStatus())) {
                    sqlUpdate = "UPDATE ReservationEquipment SET Status = 'Borrowed' WHERE ReservationID = ? AND EquipmentID = ?";
                } else if ("Returned".equals(equipment.getStatus())) {
                    sqlUpdate = "UPDATE ReservationEquipment SET Status = 'Success' WHERE ReservationID = ? AND EquipmentID = ?";
                } else {
                    continue;
                }
                pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setInt(1, reservationID);
                pstmtUpdate.setString(2, equipment.getEquipmentID());
                pstmtUpdate.executeUpdate();

                sqlUpdate = "UPDATE Equipment SET CurrentCampus = ?, Available = CASE WHEN CampusName = ? THEN 'Yes' ELSE 'No' END WHERE EquipmentID = ?";
                pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, userCampus);
                pstmtUpdate.setString(2, userCampus);
                pstmtUpdate.setString(3, equipment.getEquipmentID());
                pstmtUpdate.executeUpdate();

                String sqlCheck = "SELECT Available FROM Equipment WHERE EquipmentID = ?";
                pstmtCheckAvailability = conn.prepareStatement(sqlCheck);
                pstmtCheckAvailability.setString(1, equipment.getEquipmentID());
                ResultSet rsCheck = pstmtCheckAvailability.executeQuery();
                if (rsCheck.next() && "Yes".equals(rsCheck.getString("Available"))) {
                    notifyUsersOnWishlist(equipment.getEquipmentID());
                }
            }

            String sqlUpdate = "UPDATE Delivery SET Status = 'Success' WHERE DeliveryID = ?";
            pstmtUpdate = conn.prepareStatement(sqlUpdate);
            pstmtUpdate.setInt(1, deliveryID);
            pstmtUpdate.executeUpdate();

            success = true;
            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (pstmtUpdate != null) {
                pstmtUpdate.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return success;
    }

    public boolean reportDamage(int reservationID, String equipmentID, String description, String reportedBy, java.sql.Timestamp reportDate, String CampusName) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtUpdateReservationEquipment = null;
        boolean success = false;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sqlInsertDamageReport = "INSERT INTO DamageReports (EquipmentID, ReportedBy, ReportDate, Description, Status) VALUES (?, ?, ?, ?, 'Reported')";
            pstmt = conn.prepareStatement(sqlInsertDamageReport);
            pstmt.setString(1, equipmentID);
            pstmt.setString(2, reportedBy);
            pstmt.setTimestamp(3, reportDate);
            pstmt.setString(4, description);
            int affectedRows = pstmt.executeUpdate();

            String sqlUpdateReservationEquipment = "UPDATE ReservationEquipment SET Status = 'Error' WHERE ReservationID = ? AND EquipmentID = ?";
            pstmtUpdateReservationEquipment = conn.prepareStatement(sqlUpdateReservationEquipment);
            pstmtUpdateReservationEquipment.setInt(1, reservationID);
            pstmtUpdateReservationEquipment.setString(2, equipmentID);
            int updateCount = pstmtUpdateReservationEquipment.executeUpdate();

            if (affectedRows > 0 && updateCount > 0) {
                sendNotificationsToAdminByCampus(CampusName, "Equipment Damage Reported", "Damage reported for equipment ID " + equipmentID + " at campus " + CampusName);
                conn.commit();
                success = true;
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            System.err.println("SQL Exception: " + e.getMessage());
            throw e;
        }
        return success;
    }

    public List<DamageReportsBean> getAllDamageReports(String userCampus) throws SQLException, IOException {
        List<DamageReportsBean> reports = new ArrayList<>();
        String sql = "SELECT dr.ReportID, dr.EquipmentID, e.Name as EquipmentName,e.Available, e.EquipmentCondition, dr.ReportDate, dr.Description, dr.Status, u.FullName as ReportedBy, u.CampusName as ReportedByCampus "
                + "FROM DamageReports dr "
                + "JOIN Equipment e ON dr.EquipmentID = e.EquipmentID "
                + "JOIN Users u ON dr.ReportedBy = u.UserID "
                + "WHERE u.CampusName = ? And dr.Status = 'Reported'"
                + "ORDER BY dr.ReportDate DESC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DamageReportsBean report = new DamageReportsBean();
                    report.setReportID(rs.getInt("ReportID"));
                    report.setEquipmentID(rs.getString("EquipmentID"));
                    report.setEquipmentName(rs.getString("EquipmentName"));
                    report.setReportDate(rs.getTimestamp("ReportDate"));
                    report.setDescription(rs.getString("Description"));
                    report.setStatus(rs.getString("Status"));
                    report.setReportedByName(rs.getString("ReportedBy"));
                    report.setAvailable(rs.getString("Available"));
                    report.setCondition(rs.getString("EquipmentCondition"));
                    reports.add(report);
                }
            }
        }
        return reports;
    }

    public boolean updateDamageReport(String reportID, String condition, String available, String equipmentID) throws SQLException, IOException {
        boolean updated = false;
        Connection conn = null;
        PreparedStatement pstmtReport = null;
        PreparedStatement pstmtEquipment = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sqlReport = "UPDATE DamageReports SET Status = 'Confirmed' WHERE ReportID = ?";
            pstmtReport = conn.prepareStatement(sqlReport);
            pstmtReport.setString(1, reportID);
            int affectedRowsReport = pstmtReport.executeUpdate();

            String sqlEquipment = "UPDATE Equipment SET Available = ?, EquipmentCondition = ? WHERE EquipmentID = ?";
            pstmtEquipment = conn.prepareStatement(sqlEquipment);
            pstmtEquipment.setString(1, available);
            pstmtEquipment.setString(2, condition);
            pstmtEquipment.setString(3, equipmentID);
            int affectedRowsEquipment = pstmtEquipment.executeUpdate();

            if (affectedRowsReport > 0 && affectedRowsEquipment > 0) {
                conn.commit();
                updated = true;
                if ("Yes".equals(available)) {
                    notifyUsersOnWishlist(equipmentID);
                }
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (pstmtReport != null) {
                pstmtReport.close();
            }
            if (pstmtEquipment != null) {
                pstmtEquipment.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return updated;
    }

    public List<EquipmentStats> getEquipmentStats(String campusName) throws SQLException, IOException {
        List<EquipmentStats> stats = new ArrayList<>();
        String sql = "SELECT e.EquipmentID, e.Name, e.CampusName, e.EquipmentCondition, COUNT(br.RecordID) AS CheckOutCount "
                + "FROM Equipment e "
                + "LEFT JOIN ReservationEquipment re ON e.EquipmentID = re.EquipmentID "
                + "LEFT JOIN BorrowingRecords br ON re.ReservationID = br.ReservationID AND re.Status = 'Success' "
                + "WHERE e.CampusName = ? "
                + "GROUP BY e.EquipmentID, e.Name, e.CampusName, e.EquipmentCondition "
                + "ORDER BY COUNT(br.RecordID) DESC, e.EquipmentID ASC, e.EquipmentCondition ASC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, campusName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EquipmentStats stat = new EquipmentStats(
                            rs.getString("EquipmentID"),
                            rs.getString("Name"),
                            rs.getString("CampusName"),
                            rs.getString("EquipmentCondition"),
                            rs.getInt("CheckOutCount")
                    );
                    stats.add(stat);
                }
            }
        }
        return stats;
    }

    public List<CampusStats> getCampusStats(String userCampus) throws SQLException, IOException {
        List<CampusStats> stats = new ArrayList<>();
        String sql = "SELECT e.CampusName, COUNT(br.RecordID) AS TotalCheckOuts "
                + "FROM Equipment e "
                + "JOIN ReservationEquipment re ON e.EquipmentID = re.EquipmentID "
                + "JOIN BorrowingRecords br ON re.ReservationID = br.ReservationID "
                + "WHERE e.CampusName = ? AND re.Status = 'success' "
                + "GROUP BY e.CampusName";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CampusStats stat = new CampusStats(
                            rs.getString("CampusName"),
                            rs.getInt("TotalCheckOuts")
                    );
                    stats.add(stat);
                }
            }
        }
        return stats;
    }

    public List<DeliveryBean> fetchActiveDeliveries(String userCampus) throws SQLException, IOException {
        List<DeliveryBean> deliveries = new ArrayList<>();
        String sql = "SELECT d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus,c.Address, d.Status, d.PickupTime, d.DeliveryTime, "
                + "GROUP_CONCAT(DISTINCT e.EquipmentID ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentIDs, "
                + "GROUP_CONCAT(DISTINCT e.Name ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentNames, "
                + "GROUP_CONCAT(DISTINCT e.Description ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentDescriptions, "
                + "GROUP_CONCAT(DISTINCT e.CampusName ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentCampuses, "
                + "GROUP_CONCAT(DISTINCT e.CurrentCampus ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentCurrentCampuses, "
                + "GROUP_CONCAT(DISTINCT e.EquipmentCondition ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentConditions, "
                + "GROUP_CONCAT(DISTINCT e.ExclusiveForStaff ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentExclusiveForStaff "
                + "FROM "
                + "Delivery d "
                + "JOIN "
                + "Reservation r ON d.ReservationID = r.ReservationID "
                + "JOIN "
                + "ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN "
                + "Equipment e ON re.EquipmentID = e.EquipmentID "
                + "JOIN Campus c ON r.DestinationCampus = c.CampusName "
                + "WHERE "
                + "d.Status IN ('Scheduled') "
                + "AND d.FromCampus = ? "
                + "GROUP BY "
                + "d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus, d.Status, d.PickupTime, d.DeliveryTime "
                + "ORDER BY "
                + "d.PickupTime DESC;";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DeliveryBean delivery = new DeliveryBean();
                    delivery.setDeliveryID(rs.getInt("DeliveryID"));
                    delivery.setReservationID(rs.getInt("ReservationID"));
                    delivery.setFromCampus(rs.getString("FromCampus"));
                    delivery.setToCampus(rs.getString("ToCampus"));
                    delivery.setStatus(rs.getString("Status"));
                    delivery.setAddress(rs.getString("Address"));
                    delivery.setPickupTime(rs.getTimestamp("PickupTime"));
                    delivery.setDeliveryTime(rs.getTimestamp("DeliveryTime"));
                    delivery.setEquipmentList(fetchEquipmentListForReservationAndCampus(rs.getInt("ReservationID"), userCampus, conn));
                    deliveries.add(delivery);
                }
            }
        }
        return deliveries;
    }

    public List<DeliveryBean> fetchActiveInTransit(String userCampus) throws SQLException, IOException {
        List<DeliveryBean> deliveries = new ArrayList<>();
        String sql = "SELECT d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus, d.Status, d.PickupTime, d.DeliveryTime,c.Address "
                + "GROUP_CONCAT(DISTINCT e.EquipmentID ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentIDs, "
                + "GROUP_CONCAT(DISTINCT e.Name ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentNames, "
                + "GROUP_CONCAT(DISTINCT e.Description ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentDescriptions, "
                + "GROUP_CONCAT(DISTINCT e.CampusName ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentCampuses, "
                + "GROUP_CONCAT(DISTINCT e.CurrentCampus ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentCurrentCampuses, "
                + "GROUP_CONCAT(DISTINCT e.EquipmentCondition ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentConditions, "
                + "GROUP_CONCAT(DISTINCT e.ExclusiveForStaff ORDER BY e.EquipmentID SEPARATOR ', ') AS EquipmentExclusiveForStaff "
                + "FROM "
                + "Delivery d "
                + "JOIN "
                + "Reservation r ON d.ReservationID = r.ReservationID "
                + "JOIN "
                + "ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN "
                + "Equipment e ON re.EquipmentID = e.EquipmentID "
                + "JOIN Campus c ON r.DestinationCampus = c.CampusName "
                + "WHERE "
                + "d.Status IN ('In Transit') "
                + "AND d.FromCampus = ? "
                + "GROUP BY "
                + "d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus, d.Status, d.PickupTime, d.DeliveryTime "
                + "ORDER BY "
                + "d.PickupTime DESC;";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userCampus);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DeliveryBean delivery = new DeliveryBean();
                    delivery.setDeliveryID(rs.getInt("DeliveryID"));
                    delivery.setReservationID(rs.getInt("ReservationID"));
                    delivery.setFromCampus(rs.getString("FromCampus"));
                    delivery.setToCampus(rs.getString("ToCampus"));
                    delivery.setStatus(rs.getString("Status"));
                    delivery.setAddress(rs.getString("Address"));
                    delivery.setPickupTime(rs.getTimestamp("PickupTime"));
                    delivery.setDeliveryTime(rs.getTimestamp("DeliveryTime"));
                    delivery.setEquipmentList(fetchEquipmentListForReservationAndCampus(rs.getInt("ReservationID"), userCampus, conn));
                    deliveries.add(delivery);
                }
            }
        }
        return deliveries;
    }

    public boolean updateDeliveryStatus(int deliveryID, String newStatus, String courierID, Timestamp pickupTime) throws SQLException, IOException {
        String sql = "UPDATE Delivery SET Status = ?, CourierID = ?, PickupTime = ? WHERE DeliveryID = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, courierID);
            pstmt.setTimestamp(3, pickupTime);
            pstmt.setInt(4, deliveryID);

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw e;
        }
    }

    public boolean updateDeliveryStatus(int deliveryID, String newStatus, Timestamp DeliveryTime) throws SQLException, IOException {
        String sql = "UPDATE Delivery SET Status = ?, DeliveryTime = ? WHERE DeliveryID = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setTimestamp(2, DeliveryTime);
            pstmt.setInt(3, deliveryID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw e;
        }
    }

    public List<NotificationBean> getNotifications(String userID) throws SQLException, IOException {
        List<NotificationBean> notifications = new ArrayList<>();
        String sql = "SELECT NotificationID, Message, ReadStatus, NotificationDate FROM Notifications WHERE UserID = ? ORDER BY NotificationDate DESC";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                NotificationBean notification = new NotificationBean();
                notification.setNotificationID(rs.getInt("NotificationID"));
                notification.setMessage(rs.getString("Message"));
                notification.setReadStatus(rs.getString("ReadStatus"));
                notification.setNotificationDate(rs.getTimestamp("NotificationDate"));
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public boolean addNotification(String userID, String message) throws SQLException, IOException {
        String sql = "INSERT INTO Notifications (UserID, Message, ReadStatus, NotificationDate) VALUES (?, ?, 'Unread', CURRENT_TIMESTAMP)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, message);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean markAsRead(int notificationID) throws SQLException, IOException {
        String sql = "UPDATE Notifications SET ReadStatus = 'Read' WHERE NotificationID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, notificationID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean markAllNotificationsAsRead(String userID) throws SQLException, IOException {
        String query = "UPDATE Notifications SET ReadStatus = 'Read' WHERE UserID = ? AND ReadStatus = 'Unread'";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SQLException("Error updating notifications: " + e.getMessage(), e);
        }
    }

    public void checkOverdueItemsAndNotify() throws SQLException, IOException {
        String query = "SELECT br.RecordID, r.UserID, e.Name as EquipmentName "
                + "FROM BorrowingRecords br "
                + "JOIN Reservation r ON br.ReservationID = r.ReservationID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "WHERE re.ReturnDate IS NULL AND br.BorrowDate < CURDATE() AND re.Status = 'Borrowed'";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int recordID = rs.getInt("RecordID");
                String userID = rs.getString("UserID");
                String equipmentName = rs.getString("EquipmentName");
                sendOverdueNotification(recordID, userID, equipmentName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendOverdueNotification(int recordID, String userID, String equipmentName) throws SQLException, IOException {
        String message = "The equipment '" + equipmentName + "' with record ID " + recordID + " is overdue. Please return it as soon as possible.";
        addNotification(userID, message);
    }

    public void notifyUsersOnWishlist(String equipmentID) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT UserID FROM Wishlist WHERE EquipmentID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, equipmentID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String userID = rs.getString("UserID");
                sendNotification(userID, "Equipment available", "The equipment you wish-listed is now available!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private void sendNotificationsToTechAndAdminByCampus(String destinationCampus, String title, String message) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT UserID FROM Users WHERE (Role = 'Technician' OR Role = 'AdminTechnician') AND CampusName = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, destinationCampus);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String techID = rs.getString("UserID");
                sendNotification(techID, title, message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void sendNotification(String userID, String title, String message) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO Notifications (UserID, Message, ReadStatus, NotificationDate) VALUES (?, ?, 'Unread', CURRENT_TIMESTAMP)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            pstmt.setString(2, message);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void sendNotificationsToAdminByCampus(String campusName, String title, String message) throws SQLException, IOException {
        List<String> adminIDs = getAdminIDsByCampus(campusName);
        for (String adminID : adminIDs) {
            sendNotification(adminID, title, message);
        }
    }

    private List<String> getAdminIDsByCampus(String campusName) throws SQLException, IOException {
        List<String> adminIDs = new ArrayList<>();
        String sql = "SELECT UserID FROM Users WHERE (Role = 'AdminTechnician') AND CampusName = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, campusName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    adminIDs.add(rs.getString("UserID"));
                }
            }
        }
        return adminIDs;
    }

}
