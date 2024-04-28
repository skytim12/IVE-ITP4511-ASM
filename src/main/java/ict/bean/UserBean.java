/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Soman
 */
public class UserBean {

    private String userID;
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String CampusName;

    public UserBean() {
    }

    public UserBean(String userID, String username, String password, String role, String fullName, String CampusName) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.CampusName = CampusName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCampusName() {
        return CampusName;
    }

    public void setCampusName(String CampusName) {
        this.CampusName = CampusName;
    }
}
