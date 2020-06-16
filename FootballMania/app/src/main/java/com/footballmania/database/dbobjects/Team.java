package com.footballmania.database.dbobjects;

import androidx.annotation.NonNull;

public class Team {

    private int id;
    private String name;
    private String tla;
    private String website;
    private String area;
    private long lastUpdated;

    public Team(int id, String name, String tla, String website, String area, long lastUpdated){
        setId(id);
        setName(name);
        setTla(tla);
        setWebsite(website);
        setArea(area);
        setLastUpdated(lastUpdated);
    }

    public Team(int id, String name){
        setId(id);
        setName(name);
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

    public String getTla() {
        return tla;
    }

    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @NonNull
    @Override
    public String toString() {
        return "team: " + getName() + "\tTLA: " + getTla();
    }
}
