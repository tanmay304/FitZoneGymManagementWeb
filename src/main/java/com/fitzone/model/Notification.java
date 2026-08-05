package com.fitzone.model;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private String title;
    private String message;
    private Integer targetUserId;
    private String type;
    private Timestamp createdAt;
    private boolean read;

    public Notification() {}

    public Notification(int id, String title, String message, Integer targetUserId, String type, Timestamp createdAt, boolean read) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.targetUserId = targetUserId;
        this.type = type;
        this.createdAt = createdAt;
        this.read = read;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getTargetUserId() { return targetUserId; }
    public void setTargetUserId(Integer targetUserId) { this.targetUserId = targetUserId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
