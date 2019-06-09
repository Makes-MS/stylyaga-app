package com.example.stylyaga.Model;

public class Products {
    private String name, description, image;

    public Products(){

    }

    public Products(String pname, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getPname() {
        return name;
    }

    public void setPname(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
