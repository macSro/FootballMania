package com.footballmania.database.dbobjects;

public class Player {

    private int id;
    private String name;
    private long dateOfBirth;
    private long lastUpdated;
    private String area;
    private String position;

    public static final String POSITION_GOALKEEPER = "Goalkeeper";
    public static final String POSITION_ATTACKER = "Attacker";
    public static final String POSITION_MIDFIELDER = "Midfielder";
    public static final String POSITION_DEFENDER = "Defender";
    public static final String POSITION_COACH = "Coach";
    public static final String POSITION_NONE = "None";

    public Player(int id, String name){
        setId(id);
        setName(name);
    }

    public Player (String name, String area, int position){
        setId(id);
        setName(name);
        setDateOfBirth(dateOfBirth);
        setLastUpdated(lastUpdated);
        setArea(area);

        switch(position){
            case 1:
                setPosition(POSITION_GOALKEEPER);
                break;
            case 2:
                setPosition(POSITION_ATTACKER);
                break;
            case 3:
                setPosition(POSITION_MIDFIELDER);
                break;
            case 4:
                setPosition(POSITION_DEFENDER);
                break;
            case 5:
                setPosition(POSITION_NONE);
                break;
            case 6:
                setPosition(POSITION_COACH);
                break;

        }
    }

    public Player (int id, String name, long dateOfBirth, long lastUpdated, String area, int position){
        setId(id);
        setName(name);
        setDateOfBirth(dateOfBirth);
        setLastUpdated(lastUpdated);
        setArea(area);

        switch(position){
            case 1:
                setPosition(POSITION_GOALKEEPER);
                break;
            case 2:
                setPosition(POSITION_ATTACKER);
                break;
            case 3:
                setPosition(POSITION_MIDFIELDER);
                break;
            case 4:
                setPosition(POSITION_DEFENDER);
                break;
            case 6:
                setPosition(POSITION_COACH);
                break;
        }
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

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
