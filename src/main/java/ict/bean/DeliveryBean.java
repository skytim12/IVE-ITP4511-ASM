/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.util.List;

/**
 *
 * @author Soman
 */
public class DeliveryBean {

    private int deliveryID;
    private int reservationID;
    private int equipmentID;
    private String fromCampus;
    private String toCampus;
    private String courierID;
    private java.sql.Timestamp pickupTime;
    private java.sql.Timestamp deliveryTime;
    private String status;
    private String userName;
    private List<EquipmentBean> equipmentList;
    private String address;

    public DeliveryBean() {
    }

    public DeliveryBean(int deliveryID, int equipmentID, String fromCampus, String toCampus, String courierID, java.sql.Timestamp pickupTime, java.sql.Timestamp deliveryTime, String status) {
        this.deliveryID = deliveryID;
        this.equipmentID = equipmentID;
        this.fromCampus = fromCampus;
        this.toCampus = toCampus;
        this.courierID = courierID;
        this.pickupTime = pickupTime;
        this.deliveryTime = deliveryTime;
        this.status = status;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
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

    public String getFromCampus() {
        return fromCampus;
    }

    public void setFromCampus(String fromCampus) {
        this.fromCampus = fromCampus;
    }

    public String getToCampus() {
        return toCampus;
    }

    public void setToCampus(String toCampus) {
        this.toCampus = toCampus;
    }

    public String getCourierID() {
        return courierID;
    }

    public void setCourierID(String courierID) {
        this.courierID = courierID;
    }

    public java.sql.Timestamp getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(java.sql.Timestamp pickupTime) {
        this.pickupTime = pickupTime;
    }

    public java.sql.Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(java.sql.Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<EquipmentBean> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentBean> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
