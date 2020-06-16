package com.footballmania.database.dbobjects;

public class Season {

    private int id;
    private long startDate;
    private long endDate;
    private int currentMatchDay;

    public Season(int id, long startDate, long endDate, int currentMatchDay){
        setId(id);
        setStartDate(startDate);
        setEndDate(endDate);
        setCurrentMatchDay(currentMatchDay);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getCurrentMatchDay() {
        return currentMatchDay;
    }

    public void setCurrentMatchDay(int currentMatchDay) {
        this.currentMatchDay = currentMatchDay;
    }
}
