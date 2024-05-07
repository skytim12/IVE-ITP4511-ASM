/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.sql.Timestamp;

public class NotificationBean {

    private int notificationID;
    private String userID;
    private String message;
    private String readStatus;
    private Timestamp notificationDate;

    public NotificationBean() {
    }

    public NotificationBean(int notificationID, String userID, String message, String readStatus, Timestamp notificationDate) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.message = message;
        this.readStatus = readStatus;
        this.notificationDate = notificationDate;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public Timestamp getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Timestamp notificationDate) {
        this.notificationDate = notificationDate;
    }

}
