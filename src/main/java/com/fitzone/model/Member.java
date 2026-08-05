package com.fitzone.model;

import java.sql.Timestamp;

public class Member {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String mobile;
    private String password;
    private String state;
    private String city;
    private String address;
    private String photoPath;
    private Timestamp createDate;

    public Member() {}

    public Member(int id, String fname, String lname, String email, String mobile, String password, String state, String city, String address, String photoPath, Timestamp createDate) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.state = state;
        this.city = city;
        this.address = address;
        this.photoPath = photoPath;
        this.createDate = createDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getFullName() {
        return ((fname != null ? fname : "") + " " + (lname != null ? lname : "")).trim();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }
}
