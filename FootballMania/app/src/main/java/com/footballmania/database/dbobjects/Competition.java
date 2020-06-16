package com.footballmania.database.dbobjects;

import androidx.annotation.NonNull;

public class Competition {

    private int id;
    private String name;
    private long lastUpdated;
    private String area;

    public Competition(int id, String name){
        setId(id);
        setName(name);
    }

    public Competition(int id, String name, long lastUpdated, String area){
        setId(id);
        setName(name);
        setLastUpdated(lastUpdated);
        setArea(area);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @NonNull
    @Override
    public String toString() {
        return "competition: " + getName() + "\t area: " + getArea();
    }
}
