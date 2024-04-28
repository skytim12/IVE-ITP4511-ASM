/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Soman
 */
public class ReservationBean {

    private int reservationID;
    private int equipmentID;
    private String userID;
    private java.sql.Date reservedFrom;
    private java.sql.Date reservedTo;
    private String status;

    
    public ReservationBean() {
    }

    public ReservationBean(int reservationID, int equipmentID, String userID, java.sql.Date reservedFrom, java.sql.Date reservedTo, String status) {
        this.reservationID = reservationID;
        this.equipmentID = equipmentID;
        this.userID = userID;
        this.reservedFrom = reservedFrom;
        this.reservedTo = reservedTo;
        this.status = status;
    }

    
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public java.sql.Date getReservedFrom() {
        return reservedFrom;
    }

    public void setReservedFrom(java.sql.Date reservedFrom) {
        this.reservedFrom = reservedFrom;
    }

    public java.sql.Date getReservedTo() {
        return reservedTo;
    }

    public void setReservedTo(java.sql.Date reservedTo) {
        this.reservedTo = reservedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
