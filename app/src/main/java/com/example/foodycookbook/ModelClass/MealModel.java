package com.example.foodycookbook.ModelClass;

public class MealModel {
    String id, name, category, area, ins, thumb, yt;
    String[] ing = new String[20];
    String[] meas = new String[20];

    public MealModel(String id, String name, String category, String area, String ins, String thumb, String yt, String[] ing, String[] meas) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.ins = ins;
        this.thumb = thumb;
        this.yt = yt;
        this.ing = ing;
        this.meas = meas;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIns() {
        return ins;
    }

    public void setIns(String ins) {
        this.ins = ins;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String[] getIng() {
        return ing;
    }

    public void setIng(String[] ing) {
        this.ing = ing;
    }

    public String[] getMeas() {
        return meas;
    }

    public void setMeas(String[] meas) {
        this.meas = meas;
    }
}
