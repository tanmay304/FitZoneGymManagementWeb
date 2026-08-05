package com.fitzone.model;

import java.sql.Timestamp;

public class GymPackage {
    private int id;
    private String category;
    private String titlename;
    private String packageType;
    private String packageDuration;
    private String price;
    private String uploadphoto;
    private String description;
    private Timestamp createDate;

    public GymPackage() {}

    public GymPackage(int id, String category, String titlename, String packageType, String packageDuration, String price, String uploadphoto, String description, Timestamp createDate) {
        this.id = id;
        this.category = category;
        this.titlename = titlename;
        this.packageType = packageType;
        this.packageDuration = packageDuration;
        this.price = price;
        this.uploadphoto = uploadphoto;
        this.description = description;
        this.createDate = createDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitlename() { return titlename; }
    public void setTitlename(String titlename) { this.titlename = titlename; }

    public String getPackageType() { return packageType; }
    public void setPackageType(String packageType) { this.packageType = packageType; }

    public String getPackageDuration() { return packageDuration; }
    public void setPackageDuration(String packageDuration) { this.packageDuration = packageDuration; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getUploadphoto() { return uploadphoto; }
    public void setUploadphoto(String uploadphoto) { this.uploadphoto = uploadphoto; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }
}
