package com.example.foodycookbook.ModelClass;

public class CategoryModel {

    String id, name, thumb, des;

    public CategoryModel(String id, String name, String thumb, String des) {
        this.id = id;
        this.name = name;
        this.thumb = thumb;
        this.des = des;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
