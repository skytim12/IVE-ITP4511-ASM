/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.util.Date;

/**
 *
 * @author Soman
 */
public class ItemBean {

    private String name;
    private String campusName;
    private String condition;
    private int quantity;
    private String currentCampus;
    private Date reservedFrom;
    private Date reservedTo;

    public ItemBean(String name, String campusName, String condition, String currentCampus, int quantity, Date reservedFrom, Date reservedTo) {
        this.name = name;
        this.campusName = campusName;
        this.condition = condition;
        this.currentCampus = currentCampus;
        this.quantity = quantity;
        this.reservedFrom = reservedFrom;
        this.reservedTo = reservedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCurrentCampus() {
        return currentCampus;
    }

    public void setCurrentCampus(String currentCampus) {
        this.currentCampus = currentCampus;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getReservedFrom() {
        return reservedFrom;
    }

    public void setReservedFrom(Date reservedFrom) {
        this.reservedFrom = reservedFrom;
    }

    public Date getReservedTo() {
        return reservedTo;
    }

    public void setReservedTo(Date reservedTo) {
        this.reservedTo = reservedTo;
    }
}
