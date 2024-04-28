/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Soman
 */
public class WishlistBean {

    private int wishlistID;
    private String userID;
    private int equipmentID;
    private java.sql.Date dateAdded;

    
    public WishlistBean() {
    }

    public WishlistBean(int wishlistID, String userID, int equipmentID, java.sql.Date dateAdded) {
        this.wishlistID = wishlistID;
        this.userID = userID;
        this.equipmentID = equipmentID;
        this.dateAdded = dateAdded;
    }

    
    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public java.sql.Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(java.sql.Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
