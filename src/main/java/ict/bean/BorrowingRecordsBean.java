/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Soman
 */
public class BorrowingRecordsBean {

    private int recordID;
    private int reservationID;
    private java.sql.Date borrowDate;
    private java.sql.Date returnDate;

    
    public BorrowingRecordsBean() {
    }

    public BorrowingRecordsBean(int recordID, int reservationID, java.sql.Date borrowDate, java.sql.Date returnDate) {
        this.recordID = recordID;
        this.reservationID = reservationID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    
    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public java.sql.Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(java.sql.Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public java.sql.Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(java.sql.Date returnDate) {
        this.returnDate = returnDate;
    }
}
