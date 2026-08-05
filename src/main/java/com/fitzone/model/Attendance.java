package com.fitzone.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Attendance {
    private int id;
    private int userId;
    private Timestamp checkIn;
    private Timestamp checkOut;
    private Date attendanceDate;
    private String status;
    private String method;

    // Joined display property
    private String memberName;

    public Attendance() {}

    public Attendance(int id, int userId, Timestamp checkIn, Timestamp checkOut, Date attendanceDate, String status, String method) {
        this.id = id;
        this.userId = userId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.method = method;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getCheckIn() { return checkIn; }
    public void setCheckIn(Timestamp checkIn) { this.checkIn = checkIn; }

    public Timestamp getCheckOut() { return checkOut; }
    public void setCheckOut(Timestamp checkOut) { this.checkOut = checkOut; }

    public Date getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(Date attendanceDate) { this.attendanceDate = attendanceDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
}
