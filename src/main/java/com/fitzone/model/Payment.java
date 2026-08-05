package com.fitzone.model;

import java.sql.Timestamp;

public class Payment {
    private int id;
    private String bookingID;
    private String paymentType;
    private String payment;
    private Timestamp paymentDate;

    private String status;
    private String paymentMethod;
    private String transactionId;
    private String receiptNo;
    private String paymentReference;

    // Additional fields for display
    private String memberName;
    private String packageName;

    public Payment() {}

    public Payment(int id, String bookingID, String paymentType, String payment, Timestamp paymentDate) {
        this.id = id;
        this.bookingID = bookingID;
        this.paymentType = paymentType;
        this.payment = payment;
        this.paymentDate = paymentDate;
        this.status = "Paid";
        this.paymentMethod = "Cash";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBookingID() { return bookingID; }
    public void setBookingID(String bookingID) { this.bookingID = bookingID; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getPayment() { return payment; }
    public void setPayment(String payment) { this.payment = payment; }

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getReceiptNo() { return receiptNo; }
    public void setReceiptNo(String receiptNo) { this.receiptNo = receiptNo; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
}
