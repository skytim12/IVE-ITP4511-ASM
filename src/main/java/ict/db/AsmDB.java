/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
                "CREATE TABLE IF NOT EXISTS Users (UserID VARCHAR(20) PRIMARY KEY, "
                + "Username VARCHAR(255) UNIQUE NOT NULL, "
                + "Password VARCHAR(255) NOT NULL, "
                + "Role ENUM('GeneralUser', 'Technician', 'Courier', 'Staff', 'AdminTechnician') NOT NULL, "
                + "FullName VARCHAR(255), "
                + "Campus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'))",
                "CREATE TABLE IF NOT EXISTS Equipment (EquipmentID INT AUTO_INCREMENT PRIMARY KEY, "
                + "Name VARCHAR(255) NOT NULL, "    
                + "Description TEXT, "
                + "AvailableQuantity INT DEFAULT 0, "
                + "TotalQuantity INT DEFAULT 0, "
                + "Campus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "EquipmentCondition ENUM('New', 'Good', 'Fair', 'Poor', 'Out of Service') NOT NULL DEFAULT 'Good')",
                "CREATE TABLE IF NOT EXISTS Reservation (ReservationID INT AUTO_INCREMENT PRIMARY KEY, "
                + "EquipmentID INT, "
                + "UserID VARCHAR(20), "
                + "ReservedFrom DATE, "
                + "ReservedTo DATE, "
                + "Status ENUM('Reserved', 'Borrowed', 'Returned', 'Cancelled') DEFAULT 'Reserved', "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID))",
                "CREATE TABLE IF NOT EXISTS BorrowingRecords (RecordID INT AUTO_INCREMENT PRIMARY KEY, "
                + "ReservationID INT, "
                + "BorrowDate DATE, "
                + "ReturnDate DATE, "
                + "FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID))",
                "CREATE TABLE IF NOT EXISTS Wishlist (WishlistID INT AUTO_INCREMENT PRIMARY KEY, "
                + "UserID VARCHAR(20), "
                + "EquipmentID INT, "
                + "DateAdded DATE, "
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID))",
                "CREATE TABLE IF NOT EXISTS Delivery (DeliveryID INT AUTO_INCREMENT PRIMARY KEY, "
                + "EquipmentID INT, "
                + "FromCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "ToCampus ENUM('Chai Wan', 'Lee Wai Lee', 'Sha Tin', 'Tuen Mun', 'Tsing Yi'), "
                + "CourierID VARCHAR(20), "
                + "PickupTime DATETIME, "
                + "DeliveryTime DATETIME, "
                + "Status ENUM('Scheduled', 'In Transit', 'Delivered', 'Cancelled'), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
                + "FOREIGN KEY (CourierID) REFERENCES Users(UserID))",
                "CREATE TABLE IF NOT EXISTS DamageReports (ReportID INT AUTO_INCREMENT PRIMARY KEY, "
                + "EquipmentID INT, "
                + "    ReportedBy VARCHAR(20), "
                + "ReportDate DATETIME, "
                + "Description TEXT, "
                + "Status ENUM('Reported', 'Confirmed', 'Resolved'), "
                + "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), "
                + "FOREIGN KEY (ReportedBy) REFERENCES Users(UserID))"
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

}
