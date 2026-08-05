package com.fitzone.model;

import java.sql.Timestamp;

public class Booking {
    private int id;
    private String packageId;
    private String userId;
    private Timestamp bookingDate;
    private String payment;
    private String paymentType;

    private String status;
    private java.sql.Date expiryDate;

    // Joined fields for display
    private String memberName;
    private String packageName;

    public Booking() {}

    public Booking(int id, String packageId, String userId, Timestamp bookingDate, String payment, String paymentType) {
        this.id = id;
        this.packageId = packageId;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.payment = payment;
        this.paymentType = paymentType;
        this.status = "Active";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Timestamp getBookingDate() { return bookingDate; }
    public void setBookingDate(Timestamp bookingDate) { this.bookingDate = bookingDate; }

    public String getPayment() { return payment; }
    public void setPayment(String payment) { this.payment = payment; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public java.sql.Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(java.sql.Date expiryDate) { this.expiryDate = expiryDate; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
}
