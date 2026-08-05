package com.fitzone.model;

public class Category {
    private int id;
    private String categoryName;
    private String status;

    public Category() {}

    public Category(int id, String categoryName, String status) {
        this.id = id;
        this.categoryName = categoryName;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return categoryName;
    }
}
