package ict.bean;

import java.util.List;


public class ReservationBean {

    private int reservationID;
    private String userID;
    private String userName;  
    private java.sql.Date reservedFrom;
    private java.sql.Date reservedTo;
    private String status;
    private List<EquipmentBean> equipmentList;  

    public ReservationBean() {
    }

    public ReservationBean(int reservationID, String userID, String userName, java.sql.Date reservedFrom, java.sql.Date reservedTo, String status) {
        this.reservationID = reservationID;
        this.userID = userID;
        this.userName = userName;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<EquipmentBean> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentBean> equipmentList) {
        this.equipmentList = equipmentList;
    }
}
