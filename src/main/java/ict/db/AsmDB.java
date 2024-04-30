/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                // Create Equipment table
                "CREATE TABLE IF NOT EXISTS Equipment ("
                + "EquipmentID VARCHAR(10) PRIMARY KEY, "
                + "Name VARCHAR(255) NOT NULL, "
                + "Description TEXT, "
                + "Available ENUM('Yes', 'No') DEFAULT 'Yes', "
                + "CampusName ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "EquipmentCondition ENUM('New', 'Good', 'Fair', 'Poor', 'Out of Service') NOT NULL DEFAULT 'Good', "
                + "ExclusiveForStaff ENUM('Yes', 'No') DEFAULT 'No', " // New column
                + "FOREIGN KEY (CampusName) REFERENCES Campus(CampusName)"
                + ")",
                // Create Reservation table
                "CREATE TABLE IF NOT EXISTS Reservation ("
                + "ReservationID INT AUTO_INCREMENT PRIMARY KEY, "
                + "EquipmentID VARCHAR(10), "
                + "UserID VARCHAR(20), "
                + "ReservedFrom DATE, "
                + "ReservedTo DATE, "
                + "Status ENUM('Reserved', 'Borrowed', 'Returned', 'Cancelled') DEFAULT 'Reserved', "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID)"
                + ")",
                // Create BorrowingRecords table
                "CREATE TABLE IF NOT EXISTS BorrowingRecords ("
                + "RecordID INT AUTO_INCREMENT PRIMARY KEY, "
                + "ReservationID INT, "
                + "BorrowDate DATE, "
                + "ReturnDate DATE, "
                + "FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID)"
                + ")",
                // Create Wishlist table
                "CREATE TABLE IF NOT EXISTS Wishlist ("
                + "WishlistID INT AUTO_INCREMENT PRIMARY KEY, "
                + "UserID VARCHAR(20), "
                + "EquipmentID VARCHAR(10), "
                + "DateAdded DATE, "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID)"
                + ")",
                // Create Delivery table
                "CREATE TABLE IF NOT EXISTS Delivery ("
                + "DeliveryID INT AUTO_INCREMENT PRIMARY KEY, "
                + "EquipmentID VARCHAR(10), "
                + "FromCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "ToCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "CourierID VARCHAR(20), "
                + "PickupTime DATETIME, "
                + "DeliveryTime DATETIME, "
                + "Status ENUM('Scheduled', 'In Transit', 'Delivered', 'Cancelled'), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
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
                + "Status ENUM('Reported', 'Confirmed', 'Resolved'), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
                + "FOREIGN KEY (ReportedBy) REFERENCES Users(UserID)"
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

    public boolean isValidUser(String username, String password) throws SQLException, IOException {
        Statement stmnt = null;
        Connection cnnct = null;
        PreparedStatement pstmt = null;
        String query = "SELECT COUNT(*) FROM Users WHERE Username = ? AND Password = ?";
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            pstmt = cnnct.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public UserBean getUser(String username, String password) throws SQLException {
        String query = "SELECT UserID, Username, Password, Role, FullName, CampusName FROM Users WHERE Username = ? AND Password = ?";
        Statement stmnt = null;
        Connection cnnct = null;
        PreparedStatement pstmt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            pstmt = cnnct.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserBean user = new UserBean();
                    user.setUserID(rs.getString("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setPassword(rs.getString("Password"));
                    user.setRole(rs.getString("Role"));
                    user.setFullName(rs.getString("FullName"));
                    user.setCampusName(rs.getString("CampusName"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
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
        String query = "INSERT INTO Equipment (EquipmentID, Name, Description, Available, CampusName, EquipmentCondition, ExclusiveForStaff) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            pstmt.setString(6, equipment.getCondition());
            pstmt.setString(7, equipment.getExclusiveForStaff());

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
        String query = "SELECT EquipmentID, Name, Description, Available, CampusName, EquipmentCondition FROM Equipment";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentID(rs.getString("EquipmentID"));
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
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
        String query = "SELECT Name, Description, Available, CampusName, EquipmentCondition, COUNT(*) AS TotalQuantity FROM Equipment GROUP BY Name, Available, CampusName, EquipmentCondition";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
                equipment.setCondition(rs.getString("EquipmentCondition"));
                equipment.setTotalQuantity(rs.getInt("TotalQuantity"));
                groupedEquipmentList.add(equipment);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return groupedEquipmentList;
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
        String query = "SELECT Name, Description, Available, CampusName, EquipmentCondition, COUNT(*) AS TotalQuantity "
                + "FROM Equipment "
                + "WHERE Available = 'Yes' AND EquipmentCondition != 'Out of Service' AND ExclusiveForStaff = 'No' "
                + "GROUP BY Name, Description, Available, CampusName, EquipmentCondition";
        try (Connection cnnct = getConnection(); PreparedStatement pstmt = cnnct.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setName(rs.getString("Name"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setAvailable(rs.getString("Available"));
                equipment.setCampusName(rs.getString("CampusName"));
                equipment.setCondition(rs.getString("EquipmentCondition"));
                equipment.setTotalQuantity(rs.getInt("TotalQuantity"));
                reservableEquipmentList.add(equipment);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return reservableEquipmentList;
    }

}
