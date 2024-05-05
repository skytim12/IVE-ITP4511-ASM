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
                + "CurrentCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi') NOT NULL, " // Added current campus
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
                + "DestinationCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), " // Added destination campus
                + "Status ENUM('Reserved', 'Borrowed', 'Returned', 'Success', 'Cancelled') DEFAULT 'Reserved', "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID), "
                + "FOREIGN KEY (DestinationCampus) REFERENCES Campus(CampusName)"
                + ")",
                // Create ReservationEquipment table
                "CREATE TABLE IF NOT EXISTS ReservationEquipment ("
                + "ReservationID INT, "
                + "EquipmentID VARCHAR(10), "
                + "Status ENUM('waiting', 'accepted', 'declined', 'returned') DEFAULT 'waiting',"
                + "PRIMARY KEY (ReservationID, EquipmentID), "
                + "FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID)"
                + ")",
                // Create BorrowingRecords table
                "CREATE TABLE IF NOT EXISTS BorrowingRecords ("
                + "RecordID INT AUTO_INCREMENT PRIMARY KEY, "
                + "ReservationID INT, "
                + "BorrowDate DATE, "
                + "ReturnDate DATE, "
                + "Status ENUM('waiting', 'success', 'returned', 'fail') DEFAULT 'waiting', "
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
                + "Status ENUM('Scheduled', 'In Transit', 'Delivered', 'Cancelled'), "
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
        String sql = "INSERT INTO BorrowingRecords (ReservationID, BorrowDate, Status) VALUES (?, ?, 'waiting')";
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
        String sql = "SELECT br.RecordID, br.BorrowDate, br.ReturnDate, br.Status, "
                + "GROUP_CONCAT(DISTINCT e.Name SEPARATOR '&') AS EquipmentNames, COUNT(re.EquipmentID) AS TotalQuantity "
                + "FROM BorrowingRecords br "
                + "JOIN Reservation r ON br.ReservationID = r.ReservationID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "WHERE r.UserID = ? "
                + "GROUP BY br.RecordID, br.BorrowDate, br.ReturnDate, br.Status";

        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BorrowingRecordsBean record = new BorrowingRecordsBean();
                record.setRecordID(rs.getInt("RecordID"));
                record.setBorrowDate(rs.getDate("BorrowDate"));
                record.setReturnDate(rs.getDate("ReturnDate"));
                record.setStatus(rs.getString("Status"));
                record.setEquipmentNames(rs.getString("EquipmentNames"));
                record.setTotalQuantity(rs.getInt("TotalQuantity"));
                records.add(record);
            }
        }
        return records;
    }

    public void returnEquipment(HttpServletRequest request) throws SQLException, IOException {
        // Extract the record ID from the request
        int recordID = Integer.parseInt(request.getParameter("recordID"));
        Connection conn = null;

        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);  // Start transaction

            // Update the ReturnDate in the BorrowingRecords table
            String updateBorrowingRecordsSql = "UPDATE BorrowingRecords SET ReturnDate = CURRENT_DATE(), Status = 'returned' WHERE RecordID = ? AND ReturnDate IS NULL";
            try (PreparedStatement pstmt = conn.prepareStatement(updateBorrowingRecordsSql)) {
                pstmt.setInt(1, recordID);
                int updatedRows = pstmt.executeUpdate();
                if (updatedRows == 0) {
                    throw new SQLException("No records updated, check if the record ID is correct and the equipment hasn't been returned yet.");
                }
            }

            // Update the Status in the Reservation table
            String updateReservationSql = "UPDATE Reservation JOIN BorrowingRecords ON Reservation.ReservationID = BorrowingRecords.ReservationID "
                    + "SET Reservation.Status = 'Returned' WHERE BorrowingRecords.RecordID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateReservationSql)) {
                pstmt.setInt(1, recordID);
                int updatedRows = pstmt.executeUpdate();
                if (updatedRows == 0) {
                    throw new SQLException("Failed to update reservation status. No reservation found linked to this borrowing record.");
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
        String sql = "UPDATE Users SET FullName = ?, Password = ? WHERE UserID = ?";
        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, hashPassword(newPassword)); // Hash the password before storing it
            pstmt.setString(3, userID);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw e;
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
        String sql = "SELECT DISTINCT r.ReservationID, r.UserID, u.FullName AS userName, r.ReservedFrom, r.ReservedTo, r.Status "
                + "FROM Reservation r "
                + "JOIN Users u ON r.UserID = u.UserID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "JOIN BorrowingRecords br ON br.ReservationID = r.ReservationID "
                + "WHERE e.CampusName = ? AND br.Status = 'waiting' "
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

    public List<ReservationBean> fetchReservationsForDelivery(String userCampus) throws SQLException, IOException {
        List<ReservationBean> reservations = new ArrayList<>();
        String sql = "SELECT r.ReservationID, r.UserID,r.DestinationCampus, u.FullName AS userName, r.ReservedFrom, r.ReservedTo, r.Status "
                + "FROM Reservation r "
                + "JOIN Users u ON r.UserID = u.UserID "
                + "JOIN BorrowingRecords br ON r.ReservationID = br.ReservationID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "LEFT JOIN Delivery d ON r.ReservationID = d.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "WHERE e.CampusName = ? AND br.Status = 'success' AND d.DeliveryID IS NULL "
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
                    reservation.setStatus(rs.getString("Status"));
                    reservation.setEquipmentList(fetchEquipmentListForReservation(rs.getInt("ReservationID"), conn));
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
        String sql = "SELECT e.EquipmentID, e.Name, e.Description, e.CampusName, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff "
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
                    equipmentList.add(equipment);
                }
            }
        }
        return equipmentList;
    }

    public void updateBorrowingRecordStatus(String reservationID, String status) throws SQLException, IOException {
        String sql = "UPDATE BorrowingRecords SET Status = ? WHERE ReservationID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, reservationID);
            pstmt.executeUpdate();
        }
    }

    public boolean addDelivery(int reservationID, String fromCampus, String toCampus, String courierID, String status) throws SQLException, IOException {
        String sql = "INSERT INTO Delivery (ReservationID, FromCampus, ToCampus, CourierID, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationID);
            pstmt.setString(2, fromCampus);
            pstmt.setString(3, toCampus);
            pstmt.setString(4, courierID);
            pstmt.setString(5, status);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SQLException("Error adding delivery: " + e.getMessage(), e);
        }
    }

    public List<DeliveryBean> fetchAllDeliveries(String campusName) throws SQLException, IOException {
        List<DeliveryBean> deliveries = new ArrayList<>();
        // Expanded SQL query to include equipment details from the Equipment table
        String sql = "SELECT d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus, d.CourierID, d.PickupTime, d.DeliveryTime, d.Status, "
                + "u.FullName AS CourierName, e.Name, e.Description, e.CampusName AS EquipmentCampus, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff "
                + "FROM Delivery d "
                + "JOIN Users u ON d.CourierID = u.UserID "
                + "JOIN ReservationEquipment re ON d.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "WHERE d.ToCampus = ? AND e.CampusName = ? And d.Status = 'delivered'"
                + "ORDER BY d.PickupTime DESC";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, campusName);
            pstmt.setString(2, campusName);
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
                    delivery.setEquipmentList(fetchEquipmentListForReservation(rs.getInt("ReservationID"), conn));
                    deliveries.add(delivery);

                }
            }
        }
        return deliveries;
    }

    public boolean checkInDelivery(int deliveryID, String userCampus) throws SQLException, IOException {
        boolean success = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtEquipment = null;
        PreparedStatement pstmtReservation = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // Retrieve the ReservationID and EquipmentID from the Delivery table
            String sqlGetDeliveryInfo = "SELECT ReservationID FROM Delivery WHERE DeliveryID = ?";
            pstmt = conn.prepareStatement(sqlGetDeliveryInfo);
            pstmt.setInt(1, deliveryID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int reservationID = rs.getInt("ReservationID");

                // Update Reservation status to 'Borrowed'
                String sqlUpdateReservation = "UPDATE Reservation SET Status = 'Borrowed' WHERE ReservationID = ?";
                pstmtReservation = conn.prepareStatement(sqlUpdateReservation);
                pstmtReservation.setInt(1, reservationID);
                pstmtReservation.executeUpdate();

                // Retrieve EquipmentID linked to the reservation
                String sqlGetEquipment = "SELECT Equipment.EquipmentID, Equipment.Name,Equipment.CampusName , Equipment.Description FROM Equipment JOIN ReservationEquipment ON Equipment.EquipmentID = ReservationEquipment.EquipmentID WHERE ReservationID = ?;";
                pstmt = conn.prepareStatement(sqlGetEquipment);
                pstmt.setInt(1, reservationID);
                ResultSet rsEquipment = pstmt.executeQuery();

                while (rsEquipment.next()) {
                    String equipmentID = rsEquipment.getString("EquipmentID");
                    String campusName = rsEquipment.getString("CampusName");

                    // Update Equipment current campus and availability
                    String sqlUpdateEquipment = "UPDATE Equipment SET CurrentCampus = ?, Available = CASE WHEN ? = CampusName THEN 'Yes' ELSE Available END WHERE EquipmentID = ?";
                    pstmtEquipment = conn.prepareStatement(sqlUpdateEquipment);
                    pstmtEquipment.setString(1, userCampus);
                    pstmtEquipment.setString(2, userCampus);
                    pstmtEquipment.setString(3, equipmentID);
                    pstmtEquipment.executeUpdate();

                    // If the equipment's origin campus matches the user's current campus, update Reservation status to 'Success'
                    if (campusName.equals(userCampus)) {
                        String sqlUpdateReservationSuccess = "UPDATE Reservation SET Status = 'Success' WHERE ReservationID = ?";
                        pstmtReservation = conn.prepareStatement(sqlUpdateReservationSuccess);
                        pstmtReservation.setInt(1, reservationID);
                        pstmtReservation.executeUpdate();
                    }
                }

                success = true;
                conn.commit();
            }
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (pstmtEquipment != null) {
                pstmtEquipment.close();
            }
            if (pstmtReservation != null) {
                pstmtReservation.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return success;
    }

    public boolean reportDamage(String equipmentID, String description, String reportedBy, java.sql.Timestamp reportDate) throws SQLException, IOException {
        String sql = "INSERT INTO DamageReports (EquipmentID, ReportedBy, ReportDate, Description, Status) VALUES (?, ?, ?, ?, 'Reported')";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentID);
            pstmt.setString(2, reportedBy);
            pstmt.setTimestamp(3, reportDate);
            pstmt.setString(4, description);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw e;
        }
    }

    public List<DamageReportsBean> getAllDamageReports(String userCampus) throws SQLException, IOException {
        List<DamageReportsBean> reports = new ArrayList<>();
        String sql = "SELECT dr.ReportID, dr.EquipmentID, e.Name as EquipmentName,e.Available, e.EquipmentCondition, dr.ReportDate, dr.Description, dr.Status, u.FullName as ReportedBy, u.CampusName as ReportedByCampus "
                + "FROM DamageReports dr "
                + "JOIN Equipment e ON dr.EquipmentID = e.EquipmentID "
                + "JOIN Users u ON dr.ReportedBy = u.UserID "
                + "WHERE u.CampusName = ? "
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
            conn.setAutoCommit(false); // Start transaction

            // Update the damage report status
            String sqlReport = "UPDATE DamageReports SET Status = 'Confirmed' WHERE ReportID = ?";
            pstmtReport = conn.prepareStatement(sqlReport);
            pstmtReport.setString(1, reportID);
            int affectedRowsReport = pstmtReport.executeUpdate();

            // Update the equipment's availability and condition
            String sqlEquipment = "UPDATE Equipment SET Available = ?, EquipmentCondition = ? WHERE EquipmentID = ?";
            pstmtEquipment = conn.prepareStatement(sqlEquipment);
            pstmtEquipment.setString(1, available);
            pstmtEquipment.setString(2, condition);
            pstmtEquipment.setString(3, equipmentID);
            int affectedRowsEquipment = pstmtEquipment.executeUpdate();

            // Commit transaction if both updates are successful
            if (affectedRowsReport > 0 && affectedRowsEquipment > 0) {
                conn.commit();
                updated = true;
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
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
                + "LEFT JOIN BorrowingRecords br ON re.ReservationID = br.ReservationID AND br.Status = 'success' "
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
                + "WHERE e.CampusName = ? AND br.Status = 'success' "
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
        String sql = "SELECT d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus, d.Status, d.PickupTime, d.DeliveryTime, "
                + "e.EquipmentID, e.Name, e.Description, e.CampusName, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff "
                + "FROM Delivery d "
                + "JOIN Reservation r ON d.ReservationID = r.ReservationID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "WHERE d.Status IN ('Scheduled') AND d.FromCampus = ? "
                + "ORDER BY d.PickupTime DESC";

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
                    delivery.setPickupTime(rs.getTimestamp("PickupTime"));
                    delivery.setDeliveryTime(rs.getTimestamp("DeliveryTime"));
                    delivery.setEquipmentList(fetchEquipmentListForReservation(rs.getInt("ReservationID"), conn));
                    deliveries.add(delivery);
                }
            }
        }
        return deliveries;
    }

    public List<DeliveryBean> fetchActiveInTransit(String userCampus) throws SQLException, IOException {
        List<DeliveryBean> deliveries = new ArrayList<>();
        String sql = "SELECT d.DeliveryID, d.ReservationID, d.FromCampus, d.ToCampus, d.Status, d.PickupTime, d.DeliveryTime, "
                + "e.EquipmentID, e.Name, e.Description, e.CampusName, e.CurrentCampus, e.EquipmentCondition, e.ExclusiveForStaff "
                + "FROM Delivery d "
                + "JOIN Reservation r ON d.ReservationID = r.ReservationID "
                + "JOIN ReservationEquipment re ON r.ReservationID = re.ReservationID "
                + "JOIN Equipment e ON re.EquipmentID = e.EquipmentID "
                + "WHERE d.Status IN ('In Transit') AND d.FromCampus = ? "
                + "ORDER BY d.PickupTime DESC";

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
                    delivery.setPickupTime(rs.getTimestamp("PickupTime"));
                    delivery.setDeliveryTime(rs.getTimestamp("DeliveryTime"));
                    delivery.setEquipmentList(fetchEquipmentListForReservation(rs.getInt("ReservationID"), conn));
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

}
