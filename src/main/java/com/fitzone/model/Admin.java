package com.fitzone.model;

import java.sql.Timestamp;

public class Admin {
    private int id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private boolean mustChangePassword;
    private Timestamp createDate;

    public Admin() {}

    public Admin(int id, String name, String email, String mobile, String password, boolean mustChangePassword, Timestamp createDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.mustChangePassword = mustChangePassword;
        this.createDate = createDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isMustChangePassword() { return mustChangePassword; }
    public void setMustChangePassword(boolean mustChangePassword) { this.mustChangePassword = mustChangePassword; }

    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }
}
