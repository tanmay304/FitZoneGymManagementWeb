package com.fitzone.model;

import java.sql.Date;

public class Trainer {
    private int id;
    private String name;
    private String email;
    private String mobile;
    private String specialty;
    private double salary;
    private Date joiningDate;
    private String status;

    public Trainer() {}

    public Trainer(int id, String name, String email, String mobile, String specialty, double salary, Date joiningDate, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.specialty = specialty;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Date getJoiningDate() { return joiningDate; }
    public void setJoiningDate(Date joiningDate) { this.joiningDate = joiningDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
