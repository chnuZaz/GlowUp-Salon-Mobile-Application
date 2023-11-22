package com.example.glowup;

public class Category {

    // Properties
    private String Name;
    private String Image,Photo;
    private String Description;
    private String Benefits;
    private String Price;

    // Empty Constructor
    public Category() {
        // Default constructor (empty), can be used for Firebase deserialization.
    }

    // Constructor with parameters
    public Category(String name, String image, String description, String benefits, String price,String Photo) {
        this.Name = name;
        this.Image = image;
        this.Description = description;
        this.Benefits = benefits;
        this.Price = price;
        this.Photo = Photo;
    }

    // Getter & Setter Methods

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getBenefits() {
        return Benefits;
    }

    public void setBenefits(String benefits) {
        this.Benefits = benefits;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getPhoto() {
        return Photo;
    }
}
