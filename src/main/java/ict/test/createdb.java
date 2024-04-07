/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Soman
 */
public class createdb {

  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "itp4511_asm";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String password = "";

    try {
      Class.forName(driver).newInstance();
      Connection conn = DriverManager.getConnection(url, userName, password);

      Statement stmt = conn.createStatement();

      String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
      stmt.executeUpdate(sql);

      System.out.println("Database created successfully...");
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
